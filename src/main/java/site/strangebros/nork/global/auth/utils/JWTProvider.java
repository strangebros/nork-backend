package site.strangebros.nork.global.auth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.Clock;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import site.strangebros.nork.domain.member.entity.MemberRole;
import site.strangebros.nork.global.auth.dto.MemberAuthority;
import site.strangebros.nork.global.auth.exception.InvalidJWTException;

@RequiredArgsConstructor
@Component
public class JWTProvider implements InitializingBean {
    private static final int ACCESS_TOKEN_EXPIRATION_PERIOD = 60 * 30;
    private static final String MEMBER_ID_KEY = "memberId";
    private static final String MEMBER_ROLE_KEY = "memberRole";

    @Value("${jwt.key}")
    private String encodedKeyValue;
    private SecretKey key;

    private final Clock clock;

    @Override
    public void afterPropertiesSet() throws Exception {
        key = buildKey();
    }

    private SecretKey buildKey() {
        byte[] decodedKeyValue = Base64.getDecoder().decode(encodedKeyValue);
        return Keys.hmacShaKeyFor(decodedKeyValue);
    }

    public String buildAccessToken(MemberAuthority authority) {
        Instant now = clock.instant();

        return Jwts.builder()
                .claim(MEMBER_ID_KEY, authority.getId())
                .claim(MEMBER_ROLE_KEY, authority.getRole())
                .signWith(key)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(ACCESS_TOKEN_EXPIRATION_PERIOD)))
                .compact();
    }

    public MemberAuthority parseAccessToken(String token) {
        Claims payload = parsePayload(token);

        return MemberAuthority.builder()
                .id(parsePayload(payload.get(MEMBER_ID_KEY), int.class, MEMBER_ID_KEY))
                .role(parseMemberRolePayload(payload.get(MEMBER_ROLE_KEY)))
                .build();
    }

    private Claims parsePayload(String token) {
        Claims payload;
        try {
            payload = Jwts.parser()
                    .verifyWith(key)
                    .clock(() -> Date.from(clock.instant()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException exception) {
            throw new InvalidJWTException("만료된 token 입니다.", exception);
        } catch (JwtException exception) {
            throw new InvalidJWTException("유효하지 않은 token 입니다.", exception);
        }
        return payload;
    }

    @SuppressWarnings("unchecked")
    private <T> T parsePayload(Object raw, Class<T> targetClass, String key) {
        if (raw == null) {
            throw new IllegalArgumentException(String.format("token에 %s가 존재하지 않습니다.", key));
        }
        return (T) raw;
    }

    private MemberRole parseMemberRolePayload(Object raw) {
        if (raw == null) {
            throw new IllegalArgumentException(String.format("token에 %s가 존재하지 않습니다.", MEMBER_ROLE_KEY));
        }
        return MemberRole.from(String.valueOf(raw));
    }
}

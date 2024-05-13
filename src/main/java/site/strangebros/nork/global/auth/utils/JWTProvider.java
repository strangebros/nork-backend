package site.strangebros.nork.global.auth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import site.strangebros.nork.global.auth.exception.InvalidJWTException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Component
public class JWTProvider implements InitializingBean {
    private static final int ACCESS_TOKEN_EXPIRATION_PERIOD = 60 * 30;
    private static final String MEMBER_ID_KEY = "memberId";

    @Value("${jwt.key}")
    private String rawKey;
    private SecretKey key;

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(rawKey);
        key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    public String buildAccessToken(Integer memberId) {
        Instant now = Instant.now();

        return Jwts.builder()
                .claim(MEMBER_ID_KEY, memberId)
                .signWith(key)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(ACCESS_TOKEN_EXPIRATION_PERIOD)))
                .compact();
    }

    public Integer parseAccessToken(String token) {
        Object rawMemberId = parsePayload(token).get(MEMBER_ID_KEY);
        if (rawMemberId == null) {
            throw new InvalidJWTException("token에 memberId가 존재하지 않습니다.");
        }
        return (Integer) rawMemberId;
    }

    private Claims parsePayload(String token) {
        Claims payload;
        try {
            payload = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException exception) {
            throw new InvalidJWTException("잘못된 token 입니다.");
        }
        return payload;
    }
}

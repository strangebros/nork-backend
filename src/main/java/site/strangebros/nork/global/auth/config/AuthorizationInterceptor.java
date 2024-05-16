package site.strangebros.nork.global.auth.config;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import site.strangebros.nork.domain.member.service.MemberService;
import site.strangebros.nork.domain.member.service.dto.request.LoginRequest;
import site.strangebros.nork.global.auth.dto.MemberAuthority;
import site.strangebros.nork.global.auth.utils.JWTProvider;

import static org.apache.tomcat.websocket.Constants.AUTHORIZATION_HEADER_NAME;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String TOKEN_TYPE = "Bearer";

    private final JWTProvider jwtProvider;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        if(authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_TYPE)) {
            throw new IllegalArgumentException("로그인이 필요한 서비스입니다. 로그인 후 진행해 주세요.");
        }

        MemberAuthority authority = jwtProvider.parseAccessToken(authorizationHeader.substring(TOKEN_TYPE.length()+1));
        request.setAttribute("authority", authority);

        return true;
    }
}

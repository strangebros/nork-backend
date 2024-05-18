package site.strangebros.nork.global.auth.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import site.strangebros.nork.domain.member.entity.MemberRole;
import site.strangebros.nork.global.auth.dto.MemberAuthority;

@Slf4j

public class RoleAuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) {
        Object rawAuthority = request.getAttribute("authority");

        if (rawAuthority == null || ((MemberAuthority) rawAuthority).getRole() != MemberRole.MEMBER) {
            throw new IllegalArgumentException("회원의 role이 유효하지 않습니다.");
        }

        return true;
    }
}

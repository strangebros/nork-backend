package site.strangebros.nork.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.strangebros.nork.domain.member.entity.Member;
import site.strangebros.nork.domain.member.mapper.MemberMapper;
import site.strangebros.nork.domain.member.service.dto.request.LoginRequest;
import site.strangebros.nork.domain.member.service.dto.response.LoginResponse;
import site.strangebros.nork.global.auth.utils.JWTProvider;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;
    private final JWTProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Member member = memberMapper.findById(email);
        if (member == null || !passwordEncoder.matches(password, member.getPassword())) throw new IllegalArgumentException("아이디와 비밀번호가 일치하지 않습니다.");

        return LoginResponse.builder()
                .accessToken( jwtProvider.buildAccessToken(member.getId()))
                .build();
    }
}

package site.strangebros.nork.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.strangebros.nork.domain.member.entity.Member;
import site.strangebros.nork.domain.member.entity.MemberRole;
import site.strangebros.nork.domain.member.mapper.MemberMapper;
import site.strangebros.nork.domain.member.service.dto.request.LoginRequest;
import site.strangebros.nork.domain.member.service.dto.request.SignUpRequest;
import site.strangebros.nork.domain.member.service.dto.response.LoginResponse;
import site.strangebros.nork.domain.member.service.dto.response.SignUpResponse;
import site.strangebros.nork.global.auth.dto.MemberAuthority;
import site.strangebros.nork.global.auth.utils.JWTProvider;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;
    private final JWTProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        //비밀번호 암호화
        String encodePassword = passwordEncoder.encode(signUpRequest.getPassword());
        signUpRequest.setPassword(encodePassword);

        // Member Entity로 변경
        Member signUpInfo = signUpRequest.toMember();

        if (memberMapper.findByEmail(signUpInfo.getEmail()) != null) {
            throw new IllegalArgumentException("중복되는 이메일입니다. 다른 계정을 사용해 주세요.");
        }

        if (memberMapper.findByNickname(signUpInfo.getNickname()) != null) {
            throw new IllegalArgumentException("중복되는 닉네임입니다. 다른 닉네임을 사용해 주세요.");
        }

        // DB에 저장
        memberMapper.signUp(signUpInfo);

        Member member = memberMapper.findByEmail(signUpRequest.getEmail());

        // access 토큰 만들어서 반환
        return SignUpResponse.builder()
                .accessToken(
                        jwtProvider.buildAccessToken(MemberAuthority.builder()
                                .id(member.getId())
                                .role(MemberRole.MEMBER)
                                .build())
                ).build();
    }

    public LoginResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Member member = memberMapper.findByEmail(email);
        if (member == null || !passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("아이디와 비밀번호가 일치하지 않습니다.");
        }

        return LoginResponse.builder()
                .accessToken(
                        jwtProvider.buildAccessToken(MemberAuthority.builder()
                                .id(member.getId())
                                .role(MemberRole.MEMBER)
                                .build())
                ).build();
    }


    public LoginResponse guestLogin() {
        Member member = memberMapper.findByEmail(MemberRole.GUEST.name().toLowerCase());

        return LoginResponse.builder()
                .accessToken(
                        jwtProvider.buildAccessToken(MemberAuthority.builder()
                                .id(member.getId())
                                .role(MemberRole.GUEST)
                                .build())
                ).build();
    }
}

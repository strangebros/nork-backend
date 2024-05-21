package site.strangebros.nork.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MaxUploadSizeExceededException;
import site.strangebros.nork.domain.member.entity.Member;
import site.strangebros.nork.domain.member.mapper.MemberMapper;
import site.strangebros.nork.domain.member.service.MemberService;
import site.strangebros.nork.domain.member.service.dto.request.LoginRequest;
import site.strangebros.nork.domain.member.service.dto.request.SignUpRequest;
import site.strangebros.nork.domain.member.service.dto.request.TokenRefreshRequest;
import site.strangebros.nork.domain.member.service.dto.response.LoginResponse;

import site.strangebros.nork.domain.member.service.dto.response.MemberResponse;
import site.strangebros.nork.domain.member.service.dto.response.SignUpResponse;
import site.strangebros.nork.global.auth.config.CurrentMember;
import site.strangebros.nork.global.web.dto.response.SuccessResponse;

import java.util.Base64;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원가입
    @PostMapping("/signUp")
    public SuccessResponse<SignUpResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        // 파일 크기 검사
        String base64Image = signUpRequest.getProfileImage();
        byte[] decodedImage = Base64.getDecoder().decode(base64Image);

        // 실제 파일 크기 계산
        if(decodedImage.length > 5_242_880){ // 5MB = 5 * 1024 * 1024 bytes
            throw new MaxUploadSizeExceededException(5_242_880);
        }

        SignUpResponse signUpResponse = memberService.signUp(signUpRequest);

        return SuccessResponse.ok(signUpResponse);
    }

    // 로그인
    @PostMapping("/login")
    public SuccessResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = memberService.login(loginRequest);

        return SuccessResponse.ok(loginResponse);
    }

    // token refresh
    @PostMapping("/refresh-token")
    public SuccessResponse<LoginResponse> refreshToken(@CurrentMember int memberId, @RequestBody TokenRefreshRequest request) {
        LoginResponse loginResponse = memberService.refreshToken(memberId, request);

        return SuccessResponse.ok(loginResponse);
    }

    // GUEST 로그인
    @PostMapping("/guest-login")
    public SuccessResponse<LoginResponse> guestLogin() {
        LoginResponse loginResponse = memberService.guestLogin();

        return SuccessResponse.ok(loginResponse);
    }

    @GetMapping("/{targetMemberId}")
    public SuccessResponse<MemberResponse> getInfo(@PathVariable("targetMemberId") int targetMemberId) {
        return SuccessResponse.ok(memberService.getInfo(targetMemberId));
    }

}

package site.strangebros.nork.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import site.strangebros.nork.domain.member.entity.Member;
import site.strangebros.nork.domain.member.service.MemberService;
import site.strangebros.nork.domain.member.service.dto.request.LoginRequest;
import site.strangebros.nork.domain.member.service.dto.response.LoginResponse;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = memberService.login(loginRequest);

        return ResponseEntity.ok(loginResponse);
    }

}

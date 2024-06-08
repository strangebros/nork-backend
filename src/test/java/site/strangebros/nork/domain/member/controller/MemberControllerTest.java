package site.strangebros.nork.domain.member.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import site.strangebros.nork.domain.member.entity.MemberRole;
import site.strangebros.nork.domain.member.service.MemberService;
import site.strangebros.nork.domain.member.service.dto.request.LoginRequest;
import site.strangebros.nork.domain.member.service.dto.request.SignUpRequest;
import site.strangebros.nork.domain.member.service.dto.response.LoginResponse;
import site.strangebros.nork.global.auth.dto.MemberAuthority;
import site.strangebros.nork.global.auth.utils.JWTProvider;
import site.strangebros.nork.global.web.dto.response.SuccessResponse;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private MemberService memberService;

    @Transactional
    @Test
    public void 올바른_id와_비밀번호를_넣을시_ResponseEntity가_반환된다() throws Exception {
        // given
        SignUpRequest signUpRequest = SignUpRequest
                .builder()
                .email("hsh1769@naver.com")
                .password("1234-gmelon")
                .nickname("gmelon")
                .build();
        memberService.signUp(signUpRequest);

        LoginRequest loginRequest = LoginRequest.builder()
                .email(signUpRequest.getEmail())
                .password("1234-gmelon")
                .build();

        // when
        MockHttpServletResponse response = mvc.perform(post("/members/login")
                        .content(objectMapper.writeValueAsString(loginRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        // then
        SuccessResponse<LoginResponse> successResponse = objectMapper.readValue(response.getContentAsString(),
                new TypeReference<>() {
                });
        assertThat(successResponse.getData().getAccessToken()).isNotEmpty();
    }

    @Test
    public void 비밀번호를_입력하면_encoding된_코드를_반환한다() throws Exception {
        assertThat(passwordEncoder.matches("hjx66", passwordEncoder.encode("hjx66"))).isTrue();
    }

    @Test
    public void 올바르지_않은_id와_비밀번호를_넣을시_예외가_발생한다() throws Exception {
        LoginRequest loginRequest = LoginRequest.builder()
                .email("hsh1769@naver.com")
                .password("swim11")
                .build();

        mvc.perform(post("/members/login")
                        .content(objectMapper.writeValueAsString(loginRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

}

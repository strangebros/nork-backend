package site.strangebros.nork.domain.member.service.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import site.strangebros.nork.domain.member.entity.Member;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class SignUpRequest {
    private String email;
    private String password;
    private String nickname;
    private LocalDate birthdate;
    private String position;

    public Member toMember(){
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .birthdate(birthdate)
                .position(position)
                .build();
    }
}

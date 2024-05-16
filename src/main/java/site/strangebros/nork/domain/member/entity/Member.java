package site.strangebros.nork.domain.member.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Member {
    private int id;
    private String email;
    private String password;
    private String nickname;
    private LocalDate birthdate;
    private String position;
    private String role;

    @Builder
    public Member(int id, String email, String password, String nickname, LocalDate birthdate, String position, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.birthdate = birthdate;
        this.position = position;
        this.role = role;
    }
}



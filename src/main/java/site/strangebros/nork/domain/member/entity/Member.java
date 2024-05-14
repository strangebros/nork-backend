package site.strangebros.nork.domain.member.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
    private int id;
    private String email;
    private String password;
    private String nickname;
    private String birthdate;
    private String position;
}



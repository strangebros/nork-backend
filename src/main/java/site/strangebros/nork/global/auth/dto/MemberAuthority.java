package site.strangebros.nork.global.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.strangebros.nork.domain.member.entity.MemberRole;

@NoArgsConstructor
@Getter
public class MemberAuthority {
    private int id;
    private MemberRole role;
    private String nickname;

    @Builder
    public MemberAuthority(int id, MemberRole role, String nickname) {
        this.id = id;
        this.role = role;
        this.nickname = nickname;
    }
}

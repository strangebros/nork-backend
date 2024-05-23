package site.strangebros.nork.domain.member.service.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.strangebros.nork.domain.member.entity.Member;

@NoArgsConstructor
@Getter
public class MemberResponse {
    private int id;
    private String email;
    private String nickname;
    private String profileImage;
    private String position;
    private String role;

    @Builder
    public MemberResponse(int id, String email, String nickname, String profileImage, String position, String role) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.position = position;
        this.role = role;
    }

    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .position(member.getPosition())
                .profileImage(member.getProfileImage())
                .role(member.getRole())
                .build();
    }
}

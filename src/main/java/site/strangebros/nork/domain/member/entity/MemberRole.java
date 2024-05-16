package site.strangebros.nork.domain.member.entity;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum MemberRole {
    MEMBER,
    GUEST;

    public static MemberRole from(String name) {
        return Arrays.stream(values())
                .filter(role -> role.name().equalsIgnoreCase(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 MemberRole입니다."));
    }
}

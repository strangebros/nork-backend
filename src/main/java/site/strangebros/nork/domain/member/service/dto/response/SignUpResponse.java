package site.strangebros.nork.domain.member.service.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpResponse {
    private String accessToken;

    @Builder
    public SignUpResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}

package site.strangebros.nork.domain.member.service.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenRefreshRequest {
    private String accessToken;

    @Builder
    public TokenRefreshRequest(String accessToken){
        this.accessToken = accessToken;
    }
}

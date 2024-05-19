package site.strangebros.nork.domain.keyword.service.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class KeywordResponse {
    private int id;
    private String value;

    @Builder
    public KeywordResponse(int id, String value) {
        this.id = id;
        this.value = value;
    }
}

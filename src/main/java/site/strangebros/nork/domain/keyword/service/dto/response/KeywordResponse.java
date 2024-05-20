package site.strangebros.nork.domain.keyword.service.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.strangebros.nork.domain.keyword.entity.Keyword;

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

    public static KeywordResponse from(Keyword entity) {
        return KeywordResponse.builder()
                .id(entity.getId())
                .value(entity.getValue())
                .build();
    }
}

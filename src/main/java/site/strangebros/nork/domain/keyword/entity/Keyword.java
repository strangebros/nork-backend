package site.strangebros.nork.domain.keyword.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
public class Keyword {
    private int id;
    private String value;

    @Builder
    public Keyword(int id, String value) {
        this.id = id;
        this.value = value;
    }
}

package site.strangebros.nork.domain.review.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ReviewKeyword {
    private int id;
    private int keywordId;
    private int visitedReviewId;

    @Builder
    public ReviewKeyword(int id, int keywordId, int visitedReviewId) {
        this.id = id;
        this.keywordId = keywordId;
        this.visitedReviewId = visitedReviewId;
    }
}

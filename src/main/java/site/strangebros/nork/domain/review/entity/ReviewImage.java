package site.strangebros.nork.domain.review.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ReviewImage {
    private int id;
    private int reviewId;
    private String image;

    @Builder
    public ReviewImage(int id, int reviewId, String image) {
        this.id = id;
        this.reviewId = reviewId;
        this.image = image;
    }
}

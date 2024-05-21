package site.strangebros.nork.domain.review.service.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.strangebros.nork.domain.review.entity.Review;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class UpdateRequest {
    private String activity;
    private Double rating;
    private String reviewText;

    private List<String> images = Collections.emptyList(); // Base64 encoded images

    private List<Integer> keywords = Collections.emptyList(); // 키워드 id들

    @Builder
    public UpdateRequest(String activity, Double rating,  String reviewText, List<String> images, List<Integer> keywords) {
        this.activity = activity;
        this.rating = rating;
        this.reviewText = reviewText;
        this.images = images;
        this.keywords = keywords;
    }

    public Review toReview(int id){
        return Review.builder()
                .id(id)
                .activity(activity)
                .rating(rating)
                .reviewText(reviewText)
                .images(images)
                .keywords(keywords)
                .build();
    }
}

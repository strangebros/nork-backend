package site.strangebros.nork.domain.review.service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import site.strangebros.nork.domain.review.entity.Review;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import site.strangebros.nork.domain.review.entity.ReviewImage;


@Getter
@Setter
@Builder
public class CreateRequest {
    private int workspaceId;

    @NotNull(message = "작업 시작 시간을 설정해 주세요.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDatetime;

    @NotNull(message = "작업 종료 시간을 설정해 주세요.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDatetime;

    private String activity;
    private Double rating;
    private String reviewText;

    private List<String> images; // Base64 encoded images

    public Review toReview(Integer memberId){
        return Review.builder()
                .memberId(memberId)
                .workspaceId(workspaceId)
                .startDatetime(startDatetime)
                .endDatetime(endDatetime)
                .activity(activity)
                .rating(rating)
                .reviewText(reviewText)
                .images(images)
                .build();
    }

    public ReviewImage toReviewImage(Integer reviewId, String image){
        return ReviewImage.builder()
                .reviewId(reviewId)
                .image(image)
                .build();
    }
}

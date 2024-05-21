package site.strangebros.nork.domain.review.service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import site.strangebros.nork.domain.review.entity.Review;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import site.strangebros.nork.domain.review.entity.ReviewImage;
import site.strangebros.nork.domain.review.entity.ReviewKeyword;
import site.strangebros.nork.domain.workspace.entity.WorkspaceKeyword;

@NoArgsConstructor
@Getter
@Setter
public class CreateRequest {
    private int workspaceId;

    @NotNull(message = "작업 시작 시간을 설정해 주세요.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDatetime;

    @NotNull(message = "작업 종료 시간을 설정해 주세요.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDatetime;

    private String activity;

    @NotNull(message = "워크스페이스에 대한 별점은 필수로 들어가야 합니다.")
    private Double rating;
    private String reviewText;

    private List<String> images = Collections.emptyList(); // Base64 encoded images

    private List<Integer> keywords = Collections.emptyList(); // 키워드 id들

    @Builder
    public CreateRequest(int workspaceId, LocalDateTime startDatetime, LocalDateTime endDatetime, String activity, Double rating, List<String> images, String reviewText, List<Integer> keywords) {
        this.workspaceId = workspaceId;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.activity = activity;
        this.rating = rating;
        this.images = images;
        this.reviewText = reviewText;
        this.keywords = keywords;
    }

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
                .keywords(keywords)
                .build();
    }

    public ReviewImage toReviewImage(Integer reviewId, String image){
        return ReviewImage.builder()
                .reviewId(reviewId)
                .image(image)
                .build();
    }

    public ReviewKeyword toReviewKeyword(Integer reviewId, Integer keywordId){
        return ReviewKeyword.builder()
                .visitedReviewId(reviewId)
                .keywordId(keywordId)
                .build();
    }

    public WorkspaceKeyword toWorkspaceKeyword(Integer workspaceId, Integer keywordId){
        return WorkspaceKeyword.builder()
                .workspaceId(workspaceId)
                .keywordId(keywordId)
                .frequency(1)
                .build();
    }
}

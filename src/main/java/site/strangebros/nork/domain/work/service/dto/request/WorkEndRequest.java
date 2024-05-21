package site.strangebros.nork.domain.work.service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.strangebros.nork.domain.review.service.dto.request.CreateRequest;

@NoArgsConstructor
@Getter
public class WorkEndRequest {
    @NotNull(message = "종료할 작업의 예약 id를 설정해 주세요.")
    private int reservationId;

    // 리뷰 작성 관련 필드
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

    private List<String> images = Collections.emptyList(); // Base64 encoded images

    private List<Integer> keywords = Collections.emptyList(); // 키워드 id들

    @Builder
    public WorkEndRequest(int reservationId, int workspaceId, LocalDateTime startDatetime, LocalDateTime endDatetime,
                          String activity, Double rating, String reviewText, List<String> images,
                          List<Integer> keywords) {
        this.reservationId = reservationId;
        this.workspaceId = workspaceId;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.activity = activity;
        this.rating = rating;
        this.reviewText = reviewText;
        this.images = images;
        this.keywords = keywords;
    }

    public CreateRequest toReviewCreateRequest() {
        return CreateRequest.builder()
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
}

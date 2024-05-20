package site.strangebros.nork.domain.review.service.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReadResponse {
    private int id;
    private int memberId;
    private int workspaceId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDatetime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDatetime;

    private String activity;
    private Double rating;
    private String reviewText;

    @Builder.Default
    private List<ReadImageResponse> images = new ArrayList<>();

    @Builder.Default
    private List<ReadKeywordResponse> keywords = new ArrayList<>();

    // 해당 리뷰에 대한 이미지 정보
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReadImageResponse {
        private int visitedReviewImageId;
        private String image;
    }

    // 해당 리뷰에 대한 키워드 정보
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReadKeywordResponse {
        private int reviewKeywordId;
        private String value;
    }
}

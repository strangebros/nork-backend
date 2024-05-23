package site.strangebros.nork.domain.review.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@Getter
@Setter
public class Review {
    private int id;
    private int memberId;
    private int workspaceId;
    private String workspaceName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDatetime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDatetime;

    private String activity;
    private Double rating;
    private String reviewText;

    @Builder.Default
    private List<String> images = new ArrayList<>();

    @Builder.Default
    private List<Integer> keywords = new ArrayList<>();

    @Builder
    public Review(int id, int memberId, int workspaceId, String workspaceName, LocalDateTime startDatetime,
                  LocalDateTime endDatetime, String activity, Double rating, String reviewText, List<String> images,
                  List<Integer> keywords) {
        this.id = id;
        this.memberId = memberId;
        this.workspaceId = workspaceId;
        this.workspaceName = workspaceName;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.activity = activity;
        this.rating = rating;
        this.reviewText = reviewText;
        this.images = images;
        this.keywords = keywords;
    }
}

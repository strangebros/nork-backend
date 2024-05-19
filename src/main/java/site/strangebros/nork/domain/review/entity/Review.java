package site.strangebros.nork.domain.review.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class Review {
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
    private List<String> images;

    @Builder
    public Review(int id, int workspaceId, int memberId, LocalDateTime startDatetime, LocalDateTime endDatetime, String activity, Double rating, String reviewText, List<String> images) {
        this.id = id;
        this.workspaceId = workspaceId;
        this.memberId = memberId;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.activity = activity;
        this.rating = rating;
        this.reviewText = reviewText;
        this.images = images;
    }
}

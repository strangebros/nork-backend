package site.strangebros.nork.domain.workspace.service.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class SearchPopularWorkspaceResponse {

    private Integer id;
    private String name;
    private String category;
    private Double latitude;
    private Double longitude;
    private String roadAddress;
    private Double rating;
    private String poiId;

    private LocalDateTime recentVisitedDate;

    private List<String> keywords;

    @Builder
    public SearchPopularWorkspaceResponse(Integer id, String name, String category, Double latitude, Double longitude, String roadAddress, Double rating, String poiId, LocalDateTime recentVisitedDate, List<String> keywords) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.latitude = latitude;
        this.longitude = longitude;
        this.roadAddress = roadAddress;
        this.rating = rating;
        this.poiId = poiId;
        this.recentVisitedDate = recentVisitedDate;
        this.keywords = keywords != null ? keywords : Collections.emptyList(); // Null 안전성 보장
    }
}

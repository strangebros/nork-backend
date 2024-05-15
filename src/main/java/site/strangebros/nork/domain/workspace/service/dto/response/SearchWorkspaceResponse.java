package site.strangebros.nork.domain.workspace.service.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class SearchWorkspaceResponse {

    private String poiId;
    private String name;
    private Double latitude;
    private Double longitude;
    private String roadAddress;
    private String category;

    private Boolean isWorkspaceInDatabase;
    private int id;
    private String imageUrl;
    private Double rating;
    private Map<String, Integer> currentWorkers;
    private LocalDateTime recentVisitedDate;
    private List<String> keywords;

    @Builder
    public SearchWorkspaceResponse(String poiId, String name, Double latitude, Double longitude, String roadAddress,
                                   String category, Boolean isWorkspaceInDatabase, int id, String imageUrl,
                                   Double rating, Map<String, Integer> currentWorkers,
                                   LocalDateTime recentVisitedDate, List<String> keywords) {
        this.poiId = poiId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.roadAddress = roadAddress;
        this.category = category;
        this.isWorkspaceInDatabase = isWorkspaceInDatabase;
        this.id = id;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.currentWorkers = currentWorkers;
        this.recentVisitedDate = recentVisitedDate;
        this.keywords = keywords;
    }
}

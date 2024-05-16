package site.strangebros.nork.domain.workspace.service.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.strangebros.nork.domain.keyword.entity.Keyword;
import site.strangebros.nork.domain.workspace.entity.Workspace;
import site.strangebros.nork.domain.workspace.service.client.TmapClient;

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
    private List<String> imageUrls;
    private Double rating;
    private Map<String, Integer> currentWorkers;
    private LocalDateTime recentVisitedDate;
    private List<String> keywords;

    @Builder
    public SearchWorkspaceResponse(String poiId, String name, Double latitude, Double longitude, String roadAddress,
                                   String category, Boolean isWorkspaceInDatabase, int id, List<String> imageUrls,
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
        this.imageUrls = imageUrls;
        this.rating = rating;
        this.currentWorkers = currentWorkers;
        this.recentVisitedDate = recentVisitedDate;
        this.keywords = keywords;
    }

    public static SearchWorkspaceResponse buildWithoutWorkspace(TmapClient.Response poiResponse) {
        return SearchWorkspaceResponse.builder()
                .poiId(poiResponse.getId())
                .name(poiResponse.getName())
                .latitude(Double.valueOf(poiResponse.getLatitude()))
                .longitude(Double.valueOf(poiResponse.getLongitude()))
                .roadAddress(poiResponse.getRoadAddress())
                .category(poiResponse.getCategory())
                .isWorkspaceInDatabase(false)
                .build();
    }

    // TODO 응답에 currentWorkers 추가 필요
    public static SearchWorkspaceResponse buildWithWorkspace(TmapClient.Response poiResponse, Workspace workspace) {
        return SearchWorkspaceResponse.builder()
                .poiId(poiResponse.getId())
                .name(poiResponse.getName())
                .latitude(Double.valueOf(poiResponse.getLatitude()))
                .longitude(Double.valueOf(poiResponse.getLongitude()))
                .roadAddress(poiResponse.getRoadAddress())
                .category(poiResponse.getCategory())
                .isWorkspaceInDatabase(true)
                .id(workspace.getId())
                .imageUrls(workspace.getImageUrls())
                .rating(workspace.getRating())
                .recentVisitedDate(workspace.getRecentVisitDatetime())
                .keywords(workspace.getKeywords().stream().map(Keyword::getValue).toList())
                .build();
    }
}

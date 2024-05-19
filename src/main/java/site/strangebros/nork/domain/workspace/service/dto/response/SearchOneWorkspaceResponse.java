package site.strangebros.nork.domain.workspace.service.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import site.strangebros.nork.domain.currentWorker.entity.CurrentWorker;
import site.strangebros.nork.domain.keyword.entity.Keyword;
import site.strangebros.nork.domain.workspace.entity.Workspace;
import site.strangebros.nork.domain.workspace.service.client.TmapPoiClient;

@NoArgsConstructor
@ToString
@Setter
@Getter
public class SearchOneWorkspaceResponse {
    // from tmap search
    private String poiId;
    private String name;
    private Double latitude;
    private Double longitude;
    private String roadAddress;
    private String category;

    // from workspace db
    private Boolean isWorkspaceInDatabase;
    private Integer id;
    private Double rating;
    private LocalDateTime recentVisitedDate;
    private List<String> keywords;

    // from naver image search
    private List<String> imageUrls;

    // from redis
    private List<CurrentWorkerResponse> currentWorkers;

    @Builder
    public SearchOneWorkspaceResponse(String poiId, String name, Double latitude, Double longitude, String roadAddress,
                                      String category, Boolean isWorkspaceInDatabase, int id, Double rating,
                                      LocalDateTime recentVisitedDate, List<String> keywords, List<String> imageUrls,
                                      List<CurrentWorkerResponse> currentWorkers) {
        this.poiId = poiId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.roadAddress = roadAddress;
        this.category = category;
        this.isWorkspaceInDatabase = isWorkspaceInDatabase;
        this.id = id;
        this.rating = rating;
        this.recentVisitedDate = recentVisitedDate;
        this.keywords = keywords;
        this.imageUrls = imageUrls;
        this.currentWorkers = currentWorkers;
    }

    public static SearchOneWorkspaceResponse buildWithWorkspace(Workspace workspace,
                                                                List<String> imageUrls, List<CurrentWorker> currentWorkers) {
        List<String> images = new ArrayList<>(imageUrls);
        images.addAll(workspace.getImages());
        return SearchOneWorkspaceResponse.builder()
                .poiId(workspace.getPoiId())
                .name(workspace.getName())
                .latitude(workspace.getLatitude())
                .longitude(workspace.getLongitude())
                .roadAddress(workspace.getRoadAddress())
                .category(workspace.getCategory())
                .isWorkspaceInDatabase(true)
                .id(workspace.getId())
                .rating(workspace.getRating())
                .recentVisitedDate(workspace.getRecentVisitDatetime())
                .keywords(workspace.getKeywords().stream().map(Keyword::getValue).toList())
                .imageUrls(images)
                .currentWorkers(currentWorkers.stream().map(CurrentWorkerResponse::from).toList())
                .build();
    }

    public static SearchOneWorkspaceResponse buildWithWorkspace(TmapPoiClient.Response poiResponse, Workspace workspace,
                                                                List<String> imageUrls, List<CurrentWorker> currentWorkers) {
        List<String> images = new ArrayList<>(imageUrls);
        images.addAll(workspace.getImages());
        return SearchOneWorkspaceResponse.builder()
                .poiId(poiResponse.getId())
                .name(poiResponse.getName())
                .latitude(Double.valueOf(poiResponse.getLatitude()))
                .longitude(Double.valueOf(poiResponse.getLongitude()))
                .roadAddress(poiResponse.getRoadAddress())
                .category(poiResponse.getCategory())
                .isWorkspaceInDatabase(true)
                .id(workspace.getId())
                .rating(workspace.getRating())
                .recentVisitedDate(workspace.getRecentVisitDatetime())
                .keywords(workspace.getKeywords().stream().map(Keyword::getValue).toList())
                .imageUrls(images)
                .currentWorkers(currentWorkers.stream().map(CurrentWorkerResponse::from).toList())
                .build();
    }

    public static SearchOneWorkspaceResponse buildWithoutWorkspace(TmapPoiClient.Response poiResponse) {
        return SearchOneWorkspaceResponse.builder()
                .poiId(poiResponse.getId())
                .name(poiResponse.getName())
                .latitude(Double.valueOf(poiResponse.getLatitude()))
                .longitude(Double.valueOf(poiResponse.getLongitude()))
                .roadAddress(poiResponse.getRoadAddress())
                .category(poiResponse.getCategory())
                .isWorkspaceInDatabase(false)
                .build();
    }

    @NoArgsConstructor
    @Setter
    @Getter
    public static class CurrentWorkerResponse {
        private int memberId;
        private String position;
        private LocalDateTime startTime;

        @Builder
        public CurrentWorkerResponse(int memberId, String position, LocalDateTime startTime) {
            this.memberId = memberId;
            this.position = position;
            this.startTime = startTime;
        }

        public static CurrentWorkerResponse from(CurrentWorker currentWorker) {
            return CurrentWorkerResponse.builder()
                    .memberId(currentWorker.getMemberId())
                    .position(currentWorker.getPosition())
                    .startTime(currentWorker.getStartTime())
                    .build();
        }
    }
}

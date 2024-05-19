package site.strangebros.nork.domain.workspace.service.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.strangebros.nork.domain.currentWorker.entity.CurrentWorker;
import site.strangebros.nork.domain.currentWorker.service.dto.CreateWorkerRequest;
import site.strangebros.nork.domain.keyword.entity.Keyword;
import site.strangebros.nork.domain.workspace.entity.Workspace;
import site.strangebros.nork.domain.workspace.service.client.TmapPoiClient;

@NoArgsConstructor
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
    private int id;
    private Double rating;
    private LocalDateTime recentVisitedDate;
    private List<String> keywords;

    // from naver image search
    private List<String> imageUrls;

    // from redis
    private List<CurrentWorkerResponse> currentWorkers;

    @Builder
    public SearchOneWorkspaceResponse(String poiId, String name, Double latitude, Double longitude, String roadAddress,
                                      String category, int id, Double rating,
                                      LocalDateTime recentVisitedDate, List<String> keywords, List<String> imageUrls,
                                      List<CurrentWorkerResponse> currentWorkers) {
        this.poiId = poiId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.roadAddress = roadAddress;
        this.category = category;
        this.id = id;
        this.rating = rating;
        this.recentVisitedDate = recentVisitedDate;
        this.keywords = keywords;
        this.imageUrls = imageUrls;
        this.currentWorkers = currentWorkers;
    }

    public static SearchOneWorkspaceResponse of(TmapPoiClient.Response poiResponse, Workspace workspace,
                                                List<String> imageUrls, List<CurrentWorker> currentWorkers) {
        return SearchOneWorkspaceResponse.builder()
                .poiId(poiResponse.getId())
                .name(poiResponse.getName())
                .latitude(Double.valueOf(poiResponse.getLatitude()))
                .longitude(Double.valueOf(poiResponse.getLongitude()))
                .roadAddress(poiResponse.getRoadAddress())
                .category(poiResponse.getCategory())
                .id(workspace.getId())
                .rating(workspace.getRating())
                .recentVisitedDate(workspace.getRecentVisitDatetime())
                .keywords(workspace.getKeywords().stream().map(Keyword::getValue).toList())
                .imageUrls(imageUrls)
                .currentWorkers(currentWorkers.stream().map(CurrentWorkerResponse::from).toList())
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

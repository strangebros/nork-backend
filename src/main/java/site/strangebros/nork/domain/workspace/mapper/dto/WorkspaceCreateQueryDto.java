package site.strangebros.nork.domain.workspace.mapper.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.strangebros.nork.domain.workspace.service.client.TmapPoiClient;

@NoArgsConstructor
@Setter
@Getter
public class WorkspaceCreateQueryDto {
    private int id;
    private String name;
    private String category;
    private Double latitude;
    private Double longitude;
    private String roadAddress;
    private Double rating;
    private int numberOfVisitors;
    private String poiId;

    @Builder
    public WorkspaceCreateQueryDto(String name, String category, Double latitude, Double longitude,
                                   String roadAddress, Double rating, int numberOfVisitors, String poiId) {
        this.name = name;
        this.category = category;
        this.latitude = latitude;
        this.longitude = longitude;
        this.roadAddress = roadAddress;
        this.rating = rating;
        this.numberOfVisitors = numberOfVisitors;
        this.poiId = poiId;
    }

    public static WorkspaceCreateQueryDto from(TmapPoiClient.Response response) {
        return WorkspaceCreateQueryDto.builder()
                .name(response.getName())
                .category(response.getCategory())
                .latitude(Double.valueOf(response.getLatitude()))
                .longitude(Double.valueOf(response.getLongitude()))
                .roadAddress(response.getRoadAddress())
                .rating(0.0)
                .numberOfVisitors(0)
                .poiId(response.getId())
                .build();
    }
}

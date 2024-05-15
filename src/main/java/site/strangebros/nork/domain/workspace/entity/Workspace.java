package site.strangebros.nork.domain.workspace.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Workspace {
    private int id;
    private String name;
    private String category;
    private Double latitude;
    private Double longitude;
    private String roadAddress;
    private String imageUrl;
    private Double rating;
    private Integer numberOfVisitors;
    private String poiId;

    @Builder
    public Workspace(int id, String name, String category, Double latitude, Double longitude, String roadAddress,
                     String imageUrl, Double rating, Integer numberOfVisitors, String poiId) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.latitude = latitude;
        this.longitude = longitude;
        this.roadAddress = roadAddress;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.numberOfVisitors = numberOfVisitors;
        this.poiId = poiId;
    }
}

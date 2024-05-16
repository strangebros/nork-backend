package site.strangebros.nork.domain.workspace.entity;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import site.strangebros.nork.domain.keyword.entity.Keyword;

@NoArgsConstructor
@ToString
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

    private List<Keyword> keywords;
    private LocalDateTime recentVisitDatetime;

    @Builder
    public Workspace(int id, String name, String category, Double latitude, Double longitude, String roadAddress,
                     String imageUrl, Double rating, Integer numberOfVisitors, String poiId, List<Keyword> keywords,
                     LocalDateTime recentVisitDatetime) {
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
        this.keywords = keywords;
        this.recentVisitDatetime = recentVisitDatetime;
    }
}

package site.strangebros.nork.domain.workspace.service.dto.request;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class SearchWorkspaceRequest {
    private String query;
    private Double latitude;
    private Double longitude;
    private int radius;
    private String category;
    private List<String> keywords;

    @Builder
    public SearchWorkspaceRequest(String query, Double latitude, Double longitude, int radius, String category,
                                  List<String> keywords) {
        this.query = query;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.category = category;
        this.keywords = keywords;
    }
}

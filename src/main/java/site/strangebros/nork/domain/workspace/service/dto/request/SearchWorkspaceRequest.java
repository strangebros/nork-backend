package site.strangebros.nork.domain.workspace.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class SearchWorkspaceRequest {
    @NotEmpty(message = "workspace 검색을 위해서는 query가 반드시 필요합니다.")
    private String query;
    @NotNull(message = "workspace 검색을 위해서는 latitude가 반드시 필요합니다.")
    private Double latitude;
    @NotNull(message = "workspace 검색을 위해서는 longitude가 반드시 필요합니다.")
    private Double longitude;
    @NotNull(message = "workspace 검색을 위해서는 radius가 반드시 필요합니다.")
    private int radius;
    private String category;

    // pagination
    @NotNull(message = "workspace 검색을 위해서는 page가 반드시 필요합니다.")
    private int page;
    @NotNull(message = "workspace 검색을 위해서는 count가 반드시 필요합니다.")
    private int count;

    @Builder
    public SearchWorkspaceRequest(String query, Double latitude, Double longitude, int radius, String category,
                                  int page, int count) {
        this.query = query;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.category = category;
        this.page = page;
        this.count = count;
    }
}

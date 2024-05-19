package site.strangebros.nork.domain.workspace.service.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PoiToWorkspaceRequest {
    private String poiId;

    @Builder
    public PoiToWorkspaceRequest(String poiId) {
        this.poiId = poiId;
    }
}

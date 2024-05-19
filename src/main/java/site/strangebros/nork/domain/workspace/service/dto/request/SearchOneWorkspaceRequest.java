package site.strangebros.nork.domain.workspace.service.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class SearchOneWorkspaceRequest {
    private String poiId;

    @Builder
    public SearchOneWorkspaceRequest(String poiId) {
        this.poiId = poiId;
    }
}

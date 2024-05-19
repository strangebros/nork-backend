package site.strangebros.nork.domain.workspace.mapper.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class WorkspaceSearchOneQueryDto {
    private int memberId;
    private String poiId;

    @Builder
    public WorkspaceSearchOneQueryDto(int memberId, String poiId) {
        this.memberId = memberId;
        this.poiId = poiId;
    }
}

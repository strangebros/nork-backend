package site.strangebros.nork.domain.workspace.mapper.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class WorkspaceSearchQueryDto {
    private int memberId;
    private List<String> poiIds;

    @Builder
    public WorkspaceSearchQueryDto(int memberId, List<String> poiIds) {
        this.memberId = memberId;
        this.poiIds = poiIds;
    }
}

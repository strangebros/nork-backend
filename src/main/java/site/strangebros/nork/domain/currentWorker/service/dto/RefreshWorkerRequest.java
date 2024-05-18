package site.strangebros.nork.domain.currentWorker.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RefreshWorkerRequest {
    private Integer workspaceId;

    @Builder
    public RefreshWorkerRequest(int workspaceId) {
        this.workspaceId = workspaceId;
    }
}

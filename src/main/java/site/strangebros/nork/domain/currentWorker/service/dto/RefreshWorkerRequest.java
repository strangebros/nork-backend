package site.strangebros.nork.domain.currentWorker.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RefreshWorkerRequest {
    @NotNull(message = "workspaceId는 필수 값입니다.")
    private Integer workspaceId;

    @Builder
    public RefreshWorkerRequest(int workspaceId) {
        this.workspaceId = workspaceId;
    }
}

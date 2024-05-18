package site.strangebros.nork.domain.currentWorker.service.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import site.strangebros.nork.domain.currentWorker.entity.CurrentWorker;

@Getter
public class CreateWorkerRequest {
    private int workspaceId;
    private String position;
    private LocalDateTime startTime;

    public CurrentWorker toEntity(int memberId) {
        return CurrentWorker.builder()
                .workspaceId(workspaceId)
                .memberId(memberId)
                .position(position)
                .startTime(startTime)
                .build();
    }

    @Builder
    public CreateWorkerRequest(int workspaceId, String position, LocalDateTime startTime) {
        this.workspaceId = workspaceId;
        this.position = position;
        this.startTime = startTime;
    }
}

package site.strangebros.nork.domain.currentWorker.service.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.strangebros.nork.domain.currentWorker.entity.CurrentWorker;

@NoArgsConstructor
@Getter
public class CreateWorkerRequest {
    private int reservationId;
    private int workspaceId;
    private String position;
    private LocalDateTime startTime;

    public CurrentWorker toEntity(int memberId) {
        return CurrentWorker.builder()
                .id(reservationId)
                .memberId(memberId)
                .position(position)
                .startTime(startTime)
                .build();
    }

    @Builder
    public CreateWorkerRequest(int reservationId, int workspaceId, String position, LocalDateTime startTime) {
        this.reservationId = reservationId;
        this.workspaceId = workspaceId;
        this.position = position;
        this.startTime = startTime;
    }
}

package site.strangebros.nork.domain.currentWorker.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RefreshWorkerRequest {
    @NotNull(message = "reservationId는 필수 값입니다.")
    private Integer reservationId;

    @Builder
    public RefreshWorkerRequest(int reservationId) {
        this.reservationId = reservationId;
    }
}

package site.strangebros.nork.domain.work.service.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class WorkStartRequest {
    private int reservationId;

    @Builder
    public WorkStartRequest(int reservationId) {
        this.reservationId = reservationId;
    }
}

package site.strangebros.nork.domain.work.service.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class WorkEndRequest {
    private int reservationId;

    @Builder
    public WorkEndRequest(int reservationId) {
        this.reservationId = reservationId;
    }
}

package site.strangebros.nork.domain.reservation.service.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.strangebros.nork.domain.reservation.entity.Reservation;

@Getter
@Setter
@NoArgsConstructor
public class ReadRequest {

    private Integer workspaceId = -1;
    private Boolean all = false;

    public Reservation toReservation(Integer memberId){
        return Reservation.builder()
                .memberId(memberId)
                .workspaceId(workspaceId)
                .build();
    }

    @Builder
    public ReadRequest(Integer workspaceId, Boolean all) {
        this.workspaceId = workspaceId;
        this.all = all;
    }
}

package site.strangebros.nork.domain.reservation.service.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.strangebros.nork.domain.reservation.entity.Reservation;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class CreateRequest {
    private int memberId;
    private int workspaceId;
    private LocalDate visitStartDate;
    private String visitTimeslot;
    private String activity;
    private int activityDuration;


    public Reservation toReservation() {
        return Reservation.builder()
                .memberId(memberId)
                .workspaceId(workspaceId)
                .visitStartDate(visitStartDate)
                .visitTimeslot(visitTimeslot)
                .activity(activity)
                .activityDuration(activityDuration)
                .build();
    }
}

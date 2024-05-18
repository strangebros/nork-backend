package site.strangebros.nork.domain.reservation.service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
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
    private int workspaceId;

    @NotNull(message = "등록을 하기 위해선 작업할 날짜를 설정해 주세요.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate visitStartDate;
    private String visitTimeslot;
    private String activity;
    private int activityDuration;


    public Reservation toReservation(Integer memberId) {
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

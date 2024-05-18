package site.strangebros.nork.domain.reservation.service.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class ReadResponse {
    private int id;
    private int memberId;
    private int workspaceId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate visitStartDate;
    private String visitTimeslot;
    private String activity;
    private int activityDuration;

    @Builder
    public ReadResponse(int id, int memberId, int workspaceId, LocalDate visitStartDate, String visitTimeslot, String activity, int activityDuration) {
        this.id = id;
        this.memberId = memberId;
        this.workspaceId = workspaceId;
        this.visitStartDate = visitStartDate;
        this.visitTimeslot = visitTimeslot;
        this.activity = activity;
        this.activityDuration = activityDuration;
    }
}

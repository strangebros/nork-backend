package site.strangebros.nork.domain.work.service;

import java.time.Clock;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.strangebros.nork.domain.currentWorker.service.CurrentWorkerService;
import site.strangebros.nork.domain.currentWorker.service.dto.CreateWorkerRequest;
import site.strangebros.nork.domain.member.entity.Member;
import site.strangebros.nork.domain.member.mapper.MemberMapper;
import site.strangebros.nork.domain.reservation.entity.Reservation;
import site.strangebros.nork.domain.reservation.mapper.ReservationMapper;
import site.strangebros.nork.domain.work.service.dto.request.WorkStartRequest;

@RequiredArgsConstructor
@Service
public class WorkService {

    private final CurrentWorkerService currentWorkerService;
    private final MemberMapper memberMapper;
    private final ReservationMapper reservationMapper;
    private final Clock clock;

    public void start(int memberId, WorkStartRequest request) {
        Reservation reservation = findReservation(request);
        Member member = findMember(memberId);

        CreateWorkerRequest createWorkerRequest = CreateWorkerRequest.builder()
                .reservationId(request.getReservationId())
                .workspaceId(reservation.getWorkspaceId())
                .startTime(LocalDateTime.now(clock))
                .position(member.getPosition())
                .build();
        currentWorkerService.createWorker(memberId, createWorkerRequest);
    }

    private Reservation findReservation(WorkStartRequest request) {
        Reservation reservation = reservationMapper.findByReservationId(request.getReservationId());
        if (reservation == null) {
            throw new IllegalArgumentException("존재하지 않는 reservation id 입니다.");
        }
        return reservation;
    }

    private Member findMember(int memberId) {
        Member member = memberMapper.findById(memberId);
        if (member == null) {
            throw new IllegalArgumentException("존재하지 않는 member id 입니다.");
        }
        return member;
    }

}

package site.strangebros.nork.domain.work.service;

import java.time.Clock;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.strangebros.nork.domain.currentWorker.service.CurrentWorkerService;
import site.strangebros.nork.domain.currentWorker.service.dto.CreateWorkerRequest;
import site.strangebros.nork.domain.member.entity.Member;
import site.strangebros.nork.domain.member.mapper.MemberMapper;
import site.strangebros.nork.domain.reservation.entity.Reservation;
import site.strangebros.nork.domain.reservation.mapper.ReservationMapper;
import site.strangebros.nork.domain.review.service.ReviewService;
import site.strangebros.nork.domain.work.service.dto.request.WorkEndRequest;
import site.strangebros.nork.domain.work.service.dto.request.WorkStartRequest;

@RequiredArgsConstructor
@Service
public class WorkService {

    private final CurrentWorkerService currentWorkerService;
    private final ReviewService reviewService;
    private final MemberMapper memberMapper;
    private final ReservationMapper reservationMapper;
    private final Clock clock;

    @Transactional
    public void start(int memberId, WorkStartRequest request) {
        Reservation reservation = findReservation(request.getReservationId());
        Member member = findMember(memberId);

        if (reservation.getMemberId() != memberId) {
            throw new IllegalArgumentException("회원이 생성한 예약이 아닙니다.");
        }

        CreateWorkerRequest createWorkerRequest = CreateWorkerRequest.builder()
                .reservationId(request.getReservationId())
                .workspaceId(reservation.getWorkspaceId())
                .startTime(LocalDateTime.now(clock))
                .position(member.getPosition())
                .build();
        currentWorkerService.createWorker(memberId, createWorkerRequest);
    }

    @Transactional
    public void end(int memberId, WorkEndRequest request) {
        Reservation reservation = findReservation(request.getReservationId());

        if (reservation.getMemberId() != memberId) {
            throw new IllegalArgumentException("회원이 생성한 예약이 아닙니다.");
        }

        currentWorkerService.deleteWorker(reservation.getId());
        reviewService.createReview(request.toReviewCreateRequest(), memberId);
        reservationMapper.deleteByReservationId(request.getReservationId());
    }

    private Reservation findReservation(int reservationId) {
        Reservation reservation = reservationMapper.findByReservationId(reservationId);
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

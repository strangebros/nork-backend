package site.strangebros.nork.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.strangebros.nork.domain.reservation.entity.Reservation;
import site.strangebros.nork.domain.reservation.mapper.ReservationMapper;
import site.strangebros.nork.domain.reservation.service.dto.request.CreateRequest;
import site.strangebros.nork.domain.reservation.service.dto.response.CreateResponse;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationMapper reservationMapper;

    @Transactional
    public CreateResponse createResevation(CreateRequest createRequest) {

        // Reservation Entity로 변경
        Reservation createInfo = createRequest.toReservation();

        // DB에 저장
        reservationMapper.create(createInfo);

        Reservation reservation = reservationMapper.findByMemberIdAndWorkspaceId(createInfo.getMemberId(), createInfo.getWorkspaceId());

        // 결과값 반환
        return CreateResponse.builder()
                .id(reservation.getId())
                .memberId(reservation.getMemberId())
                .workspaceId(reservation.getWorkspaceId())
                .visitStartDate(reservation.getVisitStartDate())
                .visitTimeslot(reservation.getVisitTimeslot())
                .activity(reservation.getActivity())
                .activityDuration(reservation.getActivityDuration())
                .build();
    }
}


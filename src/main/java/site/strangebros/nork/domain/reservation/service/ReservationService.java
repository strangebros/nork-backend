package site.strangebros.nork.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.strangebros.nork.domain.reservation.entity.Reservation;
import site.strangebros.nork.domain.reservation.mapper.ReservationMapper;
import site.strangebros.nork.domain.reservation.service.dto.request.CreateRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationMapper reservationMapper;

    @Transactional
    public void createResevation(CreateRequest createRequest, Integer memberId) {

        // Reservation Entity로 변경
        Reservation createInfo = createRequest.toReservation(memberId);

        // DB에 저장
        reservationMapper.create(createInfo);

        //List<Reservation> reservations = reservationMapper.findByMemberIdsAndWorkspaceIds(createInfo.getMemberId(), createInfo.getWorkspaceId());

        //Reservation reservation = reservations.get(reservations.size() - 1);

        // 결과값 반환
        /*
        return CreateResponse.builder()
                .id(reservation.getId())
                .memberId(reservation.getMemberId())
                .workspaceId(reservation.getWorkspaceId())
                .visitStartDate(reservation.getVisitStartDate())
                .visitTimeslot(reservation.getVisitTimeslot())
                .activity(reservation.getActivity())
                .activityDuration(reservation.getActivityDuration())
                .build();

         */
    }
}


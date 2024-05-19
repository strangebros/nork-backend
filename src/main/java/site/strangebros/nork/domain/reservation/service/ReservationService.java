package site.strangebros.nork.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.strangebros.nork.domain.reservation.entity.Reservation;
import site.strangebros.nork.domain.reservation.mapper.ReservationMapper;
import site.strangebros.nork.domain.reservation.service.dto.request.CreateRequest;
import site.strangebros.nork.domain.reservation.service.dto.request.ReadRequest;
import site.strangebros.nork.domain.reservation.service.dto.response.ReadResponse;

import java.util.List;
import java.util.stream.Collectors;

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

    // 유저의 모든 예약 조회(모든 워크스페이스에 대하여)
    public List<ReadResponse> readAllReservation(ReadRequest readRequest, Integer memberId) {
        // reservation entity로 변경
        Reservation readInfo = readRequest.toReservation(memberId);

        // 검색
        List<Reservation> reservations = reservationMapper.findAllByMemberId(readInfo.getMemberId());

        // 가져온 리스트를 readResponse 리스트로 변환
        return reservations.stream()
                .map(this::convertToReadResponse)
                .collect(Collectors.toList());
    }

    // 유저의 상위 3개 예약 조회(단일 워크스페이스에 대하여, 현재보다 이후 날짜)
    public List<ReadResponse> readWorkspaceReservation(ReadRequest readRequest, Integer memberId) {
        // reservation entity로 변경
        Reservation readInfo = readRequest.toReservation(memberId);

        // 검색
        List<Reservation> reservations = reservationMapper.findByMemberIdAndWorkspaceId(readInfo.getMemberId(), readInfo.getWorkspaceId());

        // 가져온 리스트를 readResponse 리스트로 변환
        return reservations.stream()
                .map(this::convertToReadResponse)
                .collect(Collectors.toList());
    }

    // 유저의 상위 3개 예약 조회(모든 워크스페이스에 대하여, 현재보다 이후 날짜)
    public List<ReadResponse> readReservation(ReadRequest readRequest, Integer memberId) {
        // reservation entity로 변경
        Reservation readInfo = readRequest.toReservation(memberId);

        // 검색
        List<Reservation> reservations = reservationMapper.findByMemberId(readInfo.getMemberId());

        // 가져온 리스트를 readResponse 리스트로 변환
        return reservations.stream()
                .map(this::convertToReadResponse)
                .collect(Collectors.toList());
    }

    // 작업 예약 삭제
    public void deleteReservation(int reservationId) {
        reservationMapper.deleteByReservationId(reservationId);
    }

    // Reservation 객체를 ReadResponse 로 변환.
    public ReadResponse convertToReadResponse(Reservation reservation) {
        return ReadResponse.builder()
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


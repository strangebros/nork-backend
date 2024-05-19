package site.strangebros.nork.domain.reservation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.strangebros.nork.domain.reservation.service.ReservationService;
import site.strangebros.nork.domain.reservation.service.dto.request.CreateRequest;
import site.strangebros.nork.domain.reservation.service.dto.request.ReadRequest;
import site.strangebros.nork.domain.reservation.service.dto.request.UpdateRequest;
import site.strangebros.nork.domain.reservation.service.dto.response.ReadResponse;
import site.strangebros.nork.global.auth.config.CurrentMember;
import site.strangebros.nork.global.web.dto.response.SuccessResponse;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // 작업 예약 등록
    // 성공, 실패 여부만 return
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public SuccessResponse<?> createReservation(@RequestBody @Valid CreateRequest createRequest, @CurrentMember Integer memberId) {
        reservationService.createResevation(createRequest, memberId);

        return SuccessResponse.created();
    }

    // 작업 예약 조회
    // 여러 예약 찾기
    @GetMapping()
    public SuccessResponse<List<ReadResponse>> readReservations(@ModelAttribute ReadRequest readRequest, @CurrentMember Integer memberId) {
        List<ReadResponse> reservations;

        // 유저의 모든 예약 조회(모든 워크스페이스에 대하여) - 마이페이지 -> 전체 예약 보기
        if(readRequest.getAll()){
            reservations = reservationService.readAllReservation(readRequest, memberId);
        }
        // 유저의 상위 3개 예약 조회(단일 워크스페이스에 대하여, 현재보다 이후 날짜) - 워크스페이스 화면
        else if(readRequest.getWorkspaceId() != -1){
            reservations = reservationService.readWorkspaceReservation(readRequest, memberId);
        }
        // 유저의 상위 3개 예약 조회(모든 워크스페이스에 대하여, 현재보다 이후 날짜) - 마이페이지 메인 화면
        else{
            reservations = reservationService.readReservation(readRequest, memberId);
        }

        return SuccessResponse.ok(reservations);
    }

    // 단일 예약 찾기 (업데이트 시 정보를 불러오기 위함)
    @GetMapping("/{id}")
    public SuccessResponse<ReadResponse> readReservationDetail(@PathVariable("id") int reservationId){
        ReadResponse reservation = reservationService.readReservationDetail(reservationId);

        return SuccessResponse.ok(reservation);
    }

    // 예약 정보 업데이트 하기
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public SuccessResponse<?> updateReservation(@PathVariable("id")int reservationId, @RequestBody @Valid UpdateRequest updateRequest, @CurrentMember Integer memberId){
        reservationService.updateReservation(reservationId, updateRequest, memberId);

        return SuccessResponse.updated();
    }

    // 작업 예약 삭제
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public SuccessResponse<?> deleteReservation(@PathVariable("id") int reservationId){
        reservationService.deleteReservation(reservationId);

        return SuccessResponse.deleted();
    }

}

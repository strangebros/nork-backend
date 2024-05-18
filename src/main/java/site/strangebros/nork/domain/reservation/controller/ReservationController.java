package site.strangebros.nork.domain.reservation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.strangebros.nork.domain.reservation.service.ReservationService;
import site.strangebros.nork.domain.reservation.service.dto.request.CreateRequest;
import site.strangebros.nork.global.auth.config.CurrentMember;
import site.strangebros.nork.global.web.dto.response.SuccessResponse;

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
}

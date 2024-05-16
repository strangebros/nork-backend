package site.strangebros.nork.domain.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.strangebros.nork.domain.reservation.service.ReservationService;
import site.strangebros.nork.domain.reservation.service.dto.request.CreateRequest;
import site.strangebros.nork.domain.reservation.service.dto.response.CreateResponse;
import site.strangebros.nork.global.web.dto.response.SuccessResponse;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // 작업 예약 등록
    @PostMapping()
    public SuccessResponse<CreateResponse> createReservation(@RequestBody CreateRequest createRequest) {
        CreateResponse createResponse = reservationService.createResevation(createRequest);

        return SuccessResponse.ok(createResponse);
    }
}

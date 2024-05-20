package site.strangebros.nork.domain.work.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.strangebros.nork.domain.work.service.WorkService;
import site.strangebros.nork.domain.work.service.dto.request.WorkEndRequest;
import site.strangebros.nork.domain.work.service.dto.request.WorkStartRequest;
import site.strangebros.nork.global.auth.config.CurrentMember;
import site.strangebros.nork.global.web.dto.response.SuccessResponse;

@RequiredArgsConstructor
@RequestMapping("/works")
@RestController
public class WorkController {

    private final WorkService workService;

    @PostMapping("/start")
    public SuccessResponse<?> start(@CurrentMember int memberId, @RequestBody @Valid WorkStartRequest request) {
        workService.start(memberId, request);
        return SuccessResponse.ok();
    }

    @PostMapping("/end")
    public SuccessResponse<?> end(@CurrentMember int memberId, @RequestBody @Valid WorkEndRequest request) {
        workService.end(memberId, request);
        return SuccessResponse.ok();
    }

}

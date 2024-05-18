package site.strangebros.nork.domain.currentWorker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.strangebros.nork.domain.currentWorker.service.CurrentWorkerService;
import site.strangebros.nork.domain.currentWorker.service.dto.RefreshWorkerRequest;
import site.strangebros.nork.global.auth.config.CurrentMember;

@RequiredArgsConstructor
@RequestMapping("/current-worker")
@RestController
public class CurrentWorkerController {

    private final CurrentWorkerService currentWorkerService;

    @PostMapping("/refresh")
    public void refresh(@CurrentMember int memberId, @RequestBody RefreshWorkerRequest request) {
        currentWorkerService.refreshWorker(memberId, request);
    }

}

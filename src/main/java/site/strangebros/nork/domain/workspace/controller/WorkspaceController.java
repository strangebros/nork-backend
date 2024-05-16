package site.strangebros.nork.domain.workspace.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.strangebros.nork.domain.workspace.service.WorkspaceService;
import site.strangebros.nork.domain.workspace.service.dto.request.SearchWorkspaceRequest;
import site.strangebros.nork.domain.workspace.service.dto.response.SearchWorkspaceResponse;
import site.strangebros.nork.global.auth.config.CurrentMember;
import site.strangebros.nork.global.web.dto.response.SuccessResponse;

@RequiredArgsConstructor
@RequestMapping("/workspaces")
@RestControllerAdvice
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @GetMapping("/search")
    public SuccessResponse<List<SearchWorkspaceResponse>> search(
            @CurrentMember int memberId,
            @ModelAttribute SearchWorkspaceRequest request
    ) {
        return SuccessResponse.ok(workspaceService.search(memberId, request));
    }

}

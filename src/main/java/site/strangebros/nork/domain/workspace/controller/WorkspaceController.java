package site.strangebros.nork.domain.workspace.controller;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import site.strangebros.nork.domain.workspace.service.WorkspaceService;
import site.strangebros.nork.domain.workspace.service.dto.request.PoiToWorkspaceRequest;
import site.strangebros.nork.domain.workspace.service.dto.request.SearchOneWorkspaceRequest;
import site.strangebros.nork.domain.workspace.service.dto.request.SearchWorkspaceRequest;
import site.strangebros.nork.domain.workspace.service.dto.response.SearchOneWorkspaceResponse;
import site.strangebros.nork.domain.workspace.service.dto.response.SearchWorkspaceResponse;
import site.strangebros.nork.global.auth.config.CurrentMember;
import site.strangebros.nork.global.web.dto.response.SuccessResponse;

@RequiredArgsConstructor
@RequestMapping("/workspaces")
@RestController
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @GetMapping("/search")
    public SuccessResponse<List<SearchWorkspaceResponse>> search(
            @CurrentMember int memberId,
            @ModelAttribute SearchWorkspaceRequest request
    ) {
        return SuccessResponse.ok(workspaceService.search(memberId, request));
    }

    @GetMapping(value = "/search", params = {"poiId"})
    public SuccessResponse<SearchOneWorkspaceResponse> searchOne(
            @CurrentMember int memberId,
            @ModelAttribute SearchOneWorkspaceRequest request
    ) {
        return SuccessResponse.ok(workspaceService.searchOne(memberId, request));
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<?>> poiToWorkspace(@RequestBody PoiToWorkspaceRequest request)
            throws MalformedURLException, URISyntaxException {
        int workspaceId = workspaceService.poiToWorkspace(request);

        return ResponseEntity.created(UriComponentsBuilder.fromHttpUrl("/workspaces")
                .path("/{id}").build(workspaceId)
                .toURL().toURI())
                .body(SuccessResponse.created());
    }

}

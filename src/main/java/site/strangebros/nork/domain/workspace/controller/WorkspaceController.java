package site.strangebros.nork.domain.workspace.controller;

import jakarta.validation.Valid;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import site.strangebros.nork.domain.workspace.service.WorkspaceService;
import site.strangebros.nork.domain.workspace.service.dto.request.PoiToWorkspaceRequest;
import site.strangebros.nork.domain.workspace.service.dto.request.SearchOneWorkspaceRequest;
import site.strangebros.nork.domain.workspace.service.dto.request.SearchWorkspaceRequest;
import site.strangebros.nork.domain.workspace.service.dto.response.SearchOneWorkspaceResponse;
import site.strangebros.nork.domain.workspace.service.dto.response.SearchPopularWorkspaceResponse;
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
            @ModelAttribute @Valid SearchWorkspaceRequest request
    ) {
        return SuccessResponse.ok(workspaceService.search(memberId, request));
    }

    @GetMapping(value = "/search", params = {"poiId"})
    public SuccessResponse<SearchOneWorkspaceResponse> searchOne(
            @CurrentMember int memberId,
            @ModelAttribute @Valid SearchOneWorkspaceRequest request
    ) {
        return SuccessResponse.ok(workspaceService.searchOne(memberId, request));
    }

    @GetMapping("/{id}")
    public SuccessResponse<SearchOneWorkspaceResponse> findOne(@CurrentMember int memberId,
                                                               @PathVariable("id") int id) {
        return SuccessResponse.ok(workspaceService.findOne(memberId, id));
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<?>> poiToWorkspace(@RequestBody @Valid PoiToWorkspaceRequest request,
                                                             @Value("${base-uri}") String baseUri)
            throws MalformedURLException, URISyntaxException {
        int workspaceId = workspaceService.poiToWorkspace(request);

        return ResponseEntity.created(UriComponentsBuilder.fromHttpUrl(baseUri + "/workspaces")
                        .path("/{id}").build(workspaceId)
                        .toURL().toURI())
                .body(SuccessResponse.created());
    }

    // 포지션 별 인기있는 장소 조회
    @GetMapping("/popular")
    public SuccessResponse<List<SearchPopularWorkspaceResponse>> searchPopularWorkspace(@RequestParam("position") String position, @CurrentMember int memberId){
        List<SearchPopularWorkspaceResponse> popularWorkspaces = workspaceService.searchPopularWorkspace(position, memberId);

        return SuccessResponse.ok(popularWorkspaces);
    }

}

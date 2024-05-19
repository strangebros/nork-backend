package site.strangebros.nork.domain.workspace.service;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.strangebros.nork.domain.currentWorker.entity.CurrentWorker;
import site.strangebros.nork.domain.currentWorker.service.CurrentWorkerService;
import site.strangebros.nork.domain.workspace.entity.Workspace;
import site.strangebros.nork.domain.workspace.mapper.WorkspaceMapper;
import site.strangebros.nork.domain.workspace.mapper.dto.WorkspaceSearchOneQueryDto;
import site.strangebros.nork.domain.workspace.mapper.dto.WorkspaceSearchQueryDto;
import site.strangebros.nork.domain.workspace.service.client.NaverImageSearchClient;
import site.strangebros.nork.domain.workspace.service.client.TmapPoiClient;
import site.strangebros.nork.domain.workspace.service.client.TmapPoisClient;
import site.strangebros.nork.domain.workspace.service.client.TmapPoisClient.Request;
import site.strangebros.nork.domain.workspace.service.client.TmapPoisClient.Response;
import site.strangebros.nork.domain.workspace.service.dto.request.SearchOneWorkspaceRequest;
import site.strangebros.nork.domain.workspace.service.dto.request.SearchWorkspaceRequest;
import site.strangebros.nork.domain.workspace.service.dto.response.SearchOneWorkspaceResponse;
import site.strangebros.nork.domain.workspace.service.dto.response.SearchWorkspaceResponse;

@RequiredArgsConstructor
@Service
public class WorkspaceService {

    private final TmapPoisClient tmapPoisClient;
    private final TmapPoiClient tmapPoiClient;
    private final NaverImageSearchClient naverImageSearchClient;

    private final CurrentWorkerService currentWorkerService;

    private final WorkspaceMapper workspaceMapper;

    public List<SearchWorkspaceResponse> search(int memberId, SearchWorkspaceRequest request) {
        Map<String, Response> poisMap = getPoisMap(request);
        Map<String, Workspace> workspaceMap = getWorkspaceMap(memberId, poisMap);

        return buildSearchResponses(poisMap, workspaceMap);
    }

    private Map<String, Response> getPoisMap(SearchWorkspaceRequest request) {
        Request tmapRequest = Request.builder()
                .searchKeyword(request.getQuery())
                .radius(request.getRadius())
                .centerLat(request.getLatitude())
                .centerLon(request.getLongitude())
                .page(request.getPage())
                .count(request.getCount())
                .build();
        return tmapPoisClient.getPois(tmapRequest).stream()
                .collect(toMap(Response::getId, Function.identity(), (oldItem, newItem) -> newItem));
    }

    private Map<String, Workspace> getWorkspaceMap(int memberId, Map<String, Response> poiMap) {
        WorkspaceSearchQueryDto queryDto = WorkspaceSearchQueryDto.builder()
                .memberId(memberId)
                .poiIds(poiMap.keySet().stream().toList())
                .build();
        return workspaceMapper.findAllByPoiIds(queryDto)
                .stream()
                .collect(toMap(Workspace::getPoiId, Function.identity(), (oldItem, newItem) -> newItem));
    }

    private List<SearchWorkspaceResponse> buildSearchResponses(Map<String, Response> poisMap,
                                                               Map<String, Workspace> workspaceMap) {
        List<SearchWorkspaceResponse> responses = new ArrayList<>();

        for (String pois : poisMap.keySet()) {
            Workspace workspace = workspaceMap.get(pois);
            if (workspace == null) {
                responses.add(SearchWorkspaceResponse.buildWithoutWorkspace(poisMap.get(pois)));
                continue;
            }
            responses.add(SearchWorkspaceResponse.buildWithWorkspace(poisMap.get(pois), workspace));
        }

        return responses;
    }

    public SearchOneWorkspaceResponse searchOne(int memberId, SearchOneWorkspaceRequest request) {
        TmapPoiClient.Response poi = tmapPoiClient.getPoi(request.getPoiId());

        WorkspaceSearchOneQueryDto queryDto = WorkspaceSearchOneQueryDto.builder()
                .memberId(memberId)
                .poiId(request.getPoiId())
                .build();
        Workspace workspace = workspaceMapper.findOneByPoiId(queryDto);

        System.out.println(workspace);

        if (workspace == null) {
            return SearchOneWorkspaceResponse.buildWithoutWorkspace(poi);
        }

        List<String> imageUrls = naverImageSearchClient.getImageUrls(poi.getRoadAddress() + " " + poi.getName());
        List<CurrentWorker> currentWorkers = currentWorkerService.getWorkersOfWorkspace(workspace.getId());

        return SearchOneWorkspaceResponse.buildWithWorkspace(poi, workspace, imageUrls, currentWorkers);
    }

}

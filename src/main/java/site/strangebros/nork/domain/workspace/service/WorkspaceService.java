package site.strangebros.nork.domain.workspace.service;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.strangebros.nork.domain.workspace.entity.Workspace;
import site.strangebros.nork.domain.workspace.mapper.WorkspaceMapper;
import site.strangebros.nork.domain.workspace.mapper.dto.WorkspaceSearchQueryDto;
import site.strangebros.nork.domain.workspace.service.client.TmapClient;
import site.strangebros.nork.domain.workspace.service.client.TmapClient.Request;
import site.strangebros.nork.domain.workspace.service.client.TmapClient.Response;
import site.strangebros.nork.domain.workspace.service.dto.request.SearchWorkspaceRequest;
import site.strangebros.nork.domain.workspace.service.dto.response.SearchWorkspaceResponse;

@RequiredArgsConstructor
@Service
public class WorkspaceService {

    private final TmapClient tmapClient;
    private final WorkspaceMapper workspaceMapper;

    // TODO 응답에 currentWorkers 추가 필요
    public List<SearchWorkspaceResponse> search(int memberId, SearchWorkspaceRequest request) {
        Map<String, Response> poiMap = getPoiMap(request);
        Map<String, Workspace> workspaceMap = getWorkspaceMap(memberId, poiMap);

        return buildSearchResponses(poiMap, workspaceMap);
    }

    private Map<String, Response> getPoiMap(SearchWorkspaceRequest request) {
        Request tmapRequest = Request.builder()
                .searchKeyword(request.getQuery())
                .radius(request.getRadius())
                .centerLat(request.getLatitude())
                .centerLon(request.getLongitude())
                .page(request.getPage())
                .count(request.getCount())
                .build();
        return tmapClient.getPois(tmapRequest).stream()
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

    private List<SearchWorkspaceResponse> buildSearchResponses(Map<String, Response> poiMap,
                                                               Map<String, Workspace> workspaceMap) {
        List<SearchWorkspaceResponse> responses = new ArrayList<>();

        for (String poi : poiMap.keySet()) {
            Workspace workspace = workspaceMap.get(poi);
            if (workspace == null) {
                responses.add(SearchWorkspaceResponse.buildWithoutWorkspace(poiMap.get(poi)));
                continue;
            }
            responses.add(SearchWorkspaceResponse.buildWithWorkspace(poiMap.get(poi), workspace));
        }

        return responses;
    }

}

package site.strangebros.nork.domain.workspace.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import site.strangebros.nork.domain.workspace.entity.Workspace;
import site.strangebros.nork.domain.workspace.mapper.dto.WorkspaceCreateQueryDto;
import site.strangebros.nork.domain.workspace.mapper.dto.WorkspaceSearchOneQueryDto;
import site.strangebros.nork.domain.workspace.mapper.dto.WorkspaceSearchQueryDto;

@Mapper
public interface WorkspaceMapper {
    List<Workspace> findAllByPoiIds(WorkspaceSearchQueryDto dto);
    Workspace findOneByPoiId(WorkspaceSearchOneQueryDto dto);
    Workspace findOneByMemberIdAndWorkspaceId(@Param("memberId") int memberId, @Param("workspaceId") int workspaceId);
    void create(WorkspaceCreateQueryDto dto);
    void updateRatingAndNumberOfVisitors(@Param("workspaceId") int workspaceId, @Param("rating") Double rating);
    List<Workspace> SearchByPosition(@Param("position") String position, @Param("memberId") int memberId);
}

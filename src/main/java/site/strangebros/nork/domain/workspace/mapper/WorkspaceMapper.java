package site.strangebros.nork.domain.workspace.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import site.strangebros.nork.domain.workspace.entity.Workspace;
import site.strangebros.nork.domain.workspace.mapper.dto.WorkspaceCreateQueryDto;
import site.strangebros.nork.domain.workspace.mapper.dto.WorkspaceSearchOneQueryDto;
import site.strangebros.nork.domain.workspace.mapper.dto.WorkspaceSearchQueryDto;

@Mapper
public interface WorkspaceMapper {
    List<Workspace> findAllByPoiIds(WorkspaceSearchQueryDto dto);
    Workspace findOneByPoiId(WorkspaceSearchOneQueryDto dto);

    void create(WorkspaceCreateQueryDto dto);
}

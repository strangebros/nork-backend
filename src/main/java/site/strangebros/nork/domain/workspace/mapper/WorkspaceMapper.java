package site.strangebros.nork.domain.workspace.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import site.strangebros.nork.domain.workspace.entity.Workspace;

@Mapper
public interface WorkspaceMapper {
    Workspace findAllByPoiIds(List<String> poiIds);
}

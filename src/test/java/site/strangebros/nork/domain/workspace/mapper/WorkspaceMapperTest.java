package site.strangebros.nork.domain.workspace.mapper;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.strangebros.nork.domain.workspace.mapper.dto.WorkspaceSearchQueryDto;

@SpringBootTest
class WorkspaceMapperTest {

    @Autowired
    private WorkspaceMapper workspaceMapper;

    @Test
    void name() {
        System.out.println(workspaceMapper.findAllByPoiIds(WorkspaceSearchQueryDto.builder()
                .memberId(1)
                .poiIds(List.of("1"))
                .build()));
    }
}

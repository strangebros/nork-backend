package site.strangebros.nork.domain.workspace.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

@Mapper
public interface VisitedSpaceMapper {
    // 해당 장소 방문했는지 조회
    Integer findByMemberIdAndWorkspaceId(@Param("memberId") int memberId, @Param("workspaceId") int workspaceId);

    // visited_space 새로 만들기.
    void createVisitedSpace(@Param("memberId") int memberId, @Param("workspaceId") int workspaceId, @Param("startDatetime") LocalDateTime startDatetime);

    // visited_space의 값 수정하기 (count + 1, recent_visited_datetime 최신으로 수정)
    void updateVisitedSpace(@Param("visitedSpaceId") Integer visitedSpaceId, @Param("startDatetime") LocalDateTime startDatetime);
}

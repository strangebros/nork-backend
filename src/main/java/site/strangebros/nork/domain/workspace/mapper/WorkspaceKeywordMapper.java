package site.strangebros.nork.domain.workspace.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import site.strangebros.nork.domain.workspace.entity.WorkspaceKeyword;

@Mapper
public interface WorkspaceKeywordMapper {
    // 워크스페이스 키워드 조회
    int findByWorkspaceIdAndKeywordId(@Param("workspaceId") int workspaceId, @Param("keywordId") int keywordId);
    
    // 워크스페이스에 대한 키워드 추가
    int createKeyword(WorkspaceKeyword workspaceKeyword);
    
    // 워크스페이스 키워드에 대한 빈도 증가
    void increaseFrequency(@Param("workspaceId") int workspaceId, @Param("keywordId") int keywordId);
}

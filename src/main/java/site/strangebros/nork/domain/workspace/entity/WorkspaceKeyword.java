package site.strangebros.nork.domain.workspace.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class WorkspaceKeyword {
    private int id;
    private int workspaceId;
    private int keywordId;
    private int frequency;

    @Builder
    public WorkspaceKeyword(int id, int workspaceId, int keywordId, int frequency) {
        this.id = id;
        this.workspaceId = workspaceId;
        this.keywordId = keywordId;
        this.frequency = frequency;
    }
}

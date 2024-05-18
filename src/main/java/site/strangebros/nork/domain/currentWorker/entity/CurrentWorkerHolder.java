package site.strangebros.nork.domain.currentWorker.entity;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash("currentWorkerHolder")
public class CurrentWorkerHolder {
    @Id
    private int workspaceId;
    private List<String> workerIds;

    @Builder
    public CurrentWorkerHolder(int workspaceId, List<String> workerIds) {
        this.workspaceId = workspaceId;
        this.workerIds = workerIds;
    }
}

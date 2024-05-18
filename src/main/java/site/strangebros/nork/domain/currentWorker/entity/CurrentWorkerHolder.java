package site.strangebros.nork.domain.currentWorker.entity;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@ToString
@Getter
@NoArgsConstructor
@RedisHash("currentWorkerHolder")
public class CurrentWorkerHolder {
    @Id
    private int workspaceId;
    private List<String> workerIds;

    public void addWorker(String workerId) {
        if (workerIds == null) {
            workerIds = new ArrayList<>();
        }

        if (workerIds.contains(workerId)) {
            return;
        }

        workerIds.add(workerId);
    }

    @Builder
    public CurrentWorkerHolder(int workspaceId, List<String> workerIds) {
        this.workspaceId = workspaceId;
        this.workerIds = workerIds;
    }
}

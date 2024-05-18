package site.strangebros.nork.domain.currentWorker.entity;

import java.time.Duration;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@RedisHash("currentWorker")
public class CurrentWorker {
    private static final int DEFAULT_LIVE_MINUTES = 10;

    @Id
    private String id;
    private int memberId;
    private String position;
    private LocalDateTime startTime;

    @TimeToLive
    private Long timeToLive;

    public void refresh() {
        timeToLive = Duration.ofMinutes(DEFAULT_LIVE_MINUTES).toSeconds();
    }

    @Builder
    public CurrentWorker(int workspaceId, int memberId, String position, LocalDateTime startTime) {
        this.id = workspaceId + "." + memberId;
        this.memberId = memberId;
        this.position = position;
        this.startTime = startTime;

        refresh();
    }
}

package site.strangebros.nork.domain.currentWorker.entity;

import java.time.Duration;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@ToString
@Getter
@NoArgsConstructor
@RedisHash("currentWorker")
public class CurrentWorker {
    private static final int DEFAULT_LIVE_MINUTES = 10;

    @Id
    private int id; // reservationId
    private int memberId;
    private String position;
    private LocalDateTime startTime;

    @Getter(AccessLevel.NONE)
    @TimeToLive
    private Long timeToLive;

    public void refresh() {
        timeToLive = Duration.ofMinutes(DEFAULT_LIVE_MINUTES).toSeconds();
    }

    @Builder
    public CurrentWorker(int id, int memberId, String position, LocalDateTime startTime) {
        this.id = id;
        this.memberId = memberId;
        this.position = position;
        this.startTime = startTime;

        refresh();
    }
}

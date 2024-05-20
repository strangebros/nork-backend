package site.strangebros.nork.domain.review.service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.strangebros.nork.domain.review.entity.Review;

@NoArgsConstructor
@Getter
@Setter
public class ReadRequest {

    private int workspaceId = -1;

    // pagination
    @NotNull(message = "review 조회를 위해서는 page가 반드시 필요합니다.")
    private int page;
    @NotNull(message = "review 조회를 위해서는 count가 반드시 필요합니다.")
    private int count;

    public Review toReview(Integer memberId){
        return Review.builder()
                .memberId(memberId)
                .workspaceId(workspaceId)
                .build();
    }

    @Builder
    public ReadRequest(int workspaceId, int page, int count) {
        this.workspaceId = workspaceId;
        this.page = page;
        this.count = count;
    }
}

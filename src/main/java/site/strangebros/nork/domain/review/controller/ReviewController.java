package site.strangebros.nork.domain.review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.strangebros.nork.domain.review.service.ReviewService;
import site.strangebros.nork.domain.review.service.dto.request.CreateRequest;
import site.strangebros.nork.global.auth.config.CurrentMember;
import site.strangebros.nork.global.web.dto.response.SuccessResponse;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 등록
    // WorkController로 이사갈 예정...
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public SuccessResponse<?> createReview(@RequestBody @Valid CreateRequest createRequest, @CurrentMember Integer memberId){
        reviewService.createReview(createRequest, memberId);

        return SuccessResponse.created();
    }
}

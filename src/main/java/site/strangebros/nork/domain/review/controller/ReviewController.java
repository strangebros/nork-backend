package site.strangebros.nork.domain.review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.strangebros.nork.domain.review.service.ReviewService;
import site.strangebros.nork.domain.review.service.dto.request.CreateRequest;
import site.strangebros.nork.domain.review.service.dto.request.ReadRequest;
import site.strangebros.nork.domain.review.service.dto.response.ReadResponse;
import site.strangebros.nork.global.auth.config.CurrentMember;
import site.strangebros.nork.global.web.dto.response.SuccessResponse;

import java.util.List;

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

    // 리뷰 조회
    @GetMapping()
    public SuccessResponse<List<ReadResponse>> readReviews(@ModelAttribute ReadRequest readRequest, @CurrentMember Integer memberId){
        List<ReadResponse> reviews;

        //System.out.println("워크스페이스 정보: "+ readRequest.getWorkspaceId());

        // 유저의 리뷰 5개씩 조회(단일 워크스페이스에 대하여)
        if(readRequest.getWorkspaceId() != -1){
            //System.out.println("워크스페이스 리뷰 조회 시작");
           reviews = reviewService.readWorkspaceReview(readRequest, memberId);
        }
        // 유저의 리뷰 10개씩 조회(모든 워크스페이스에 대하여) - 마이페이지 -> 리뷰들 조회
        else{
            //System.out.println("유저 리뷰 조회 시작");
            reviews = reviewService.readReview(readRequest, memberId);
        }

        return SuccessResponse.ok(reviews);
    }

    // 단일 리뷰 조회(업데이트 시 정보를 불러오기 위함)
    @GetMapping("/{id}")
    public SuccessResponse<ReadResponse> readReviewDetail(@PathVariable("id") int reviewId){
        ReadResponse review = reviewService.readReviewDetail(reviewId);

        return SuccessResponse.ok(review);
    }
}

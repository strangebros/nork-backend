package site.strangebros.nork.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.strangebros.nork.domain.review.entity.Review;
import site.strangebros.nork.domain.review.entity.ReviewImage;
import site.strangebros.nork.domain.review.mapper.ReviewMapper;
import site.strangebros.nork.domain.review.service.dto.request.CreateRequest;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewMapper reviewMapper;

    @Transactional
    public void createReview(CreateRequest createRequest, Integer memberId) {
        // Review Entity로 수정
        Review createInfo = createRequest.toReview(memberId);

        // DB에 저장
        reviewMapper.create(createInfo);

        // DB에 이미지 저장
        // create에 useGeneratedKeys 속성을 사용했으므로, 방금 만든 review의 id를 바로 가져올 수 있다.
        List<ReviewImage> images = createRequest.getImages().stream()
                .map(image -> createRequest.toReviewImage(createInfo.getId(), image))
                .collect(Collectors.toList());

        for (ReviewImage reviewImage : images) {
            reviewMapper.createImage(reviewImage);
        }
    }
}

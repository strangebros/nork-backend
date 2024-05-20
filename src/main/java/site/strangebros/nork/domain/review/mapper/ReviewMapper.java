package site.strangebros.nork.domain.review.mapper;

import org.apache.ibatis.annotations.Mapper;
import site.strangebros.nork.domain.review.entity.Review;
import site.strangebros.nork.domain.review.entity.ReviewImage;

import java.util.List;

@Mapper
public interface ReviewMapper {
    // 리뷰 생성
    int create(Review createInfo);

    // 리뷰 이미지 생성
    int createImage(ReviewImage createImageInfo);

    // 유저의 리뷰 5개씩 조회(단일 워크스페이스에 대하여) - 워크스페이스 페이지 -> 나의 리뷰 조회
    List<Review> findByMemberIdAndWorkspaceId(Review review, int offset, int count);

    // 유저의 리뷰 10개씩 조회(모든 워크스페이스에 대하여) - 마이페이지 -> 리뷰들 조회
    List<Review> findByMemberId(Review review, int offset, int count);

    // 단일 리뷰 조회(업데이트 시 정보를 불러오기 위함)
    Review findByReviewId(int reviewId);

    // 리뷰 조회 시 이미지 객체를 만들기 위해 이미지 조회
    ReviewImage findImageByReviewIdAndImage(int reviewId, String image);
}

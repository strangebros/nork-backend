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

    // 유저의 특정 장소에 대한 모든 리뷰 가져오기.
    List<Review> findByMemberIdAndWorkspaceId(int memberId, int workspaceId);


}

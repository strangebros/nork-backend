package site.strangebros.nork.domain.review.mapper;

import org.apache.ibatis.annotations.Mapper;
import site.strangebros.nork.domain.review.entity.ReviewKeyword;

@Mapper
public interface ReviewKewordMapper {
    // 리뷰 키워드 추가
    int createKeyword(ReviewKeyword reviewKeyword);

    // 리뷰 조회 시 키워드 객체를 만들기 위한 키워드 조회
    ReviewKeyword findKeywordByReviewIdAndKeyword(int reviewId, int keywordId);

    // 키워드 아이디로 키워드 조회(value 얻기 위함)
    String findKeywordValueByKeywordId(int keywordId);
}

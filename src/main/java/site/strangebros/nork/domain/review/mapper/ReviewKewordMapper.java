package site.strangebros.nork.domain.review.mapper;

import org.apache.ibatis.annotations.Mapper;
import site.strangebros.nork.domain.review.entity.ReviewKeyword;

@Mapper
public interface ReviewKewordMapper {
    // 리뷰 키워드 추가
    int createKeyword(ReviewKeyword reviewKeyword);
}

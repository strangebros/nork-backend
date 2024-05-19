package site.strangebros.nork.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.strangebros.nork.domain.review.entity.Review;
import site.strangebros.nork.domain.review.entity.ReviewImage;
import site.strangebros.nork.domain.review.mapper.ReviewKewordMapper;
import site.strangebros.nork.domain.review.mapper.ReviewMapper;
import site.strangebros.nork.domain.review.service.dto.request.CreateRequest;
import site.strangebros.nork.domain.workspace.entity.WorkspaceKeyword;
import site.strangebros.nork.domain.workspace.mapper.WorkspaceKeywordMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewMapper reviewMapper;
    private final ReviewKewordMapper reviewKewordMapper;
    private final WorkspaceKeywordMapper workspaceKeywordMapper;

    @Transactional
    public void createReview(CreateRequest createRequest, Integer memberId) {
        // Review keyword가 3개보다 많다면 에러 던짐
        if(!createRequest.getKeywords().isEmpty() && createRequest.getKeywords().size() > 3){
            throw new IllegalArgumentException("키워드는 3개 이하로 선택해주세요.");
        }

        // Review Entity로 수정
        Review createInfo = createRequest.toReview(memberId);

        // DB에 저장
        reviewMapper.create(createInfo);

        // DB에 이미지 저장
        // create에 useGeneratedKeys 속성을 사용했으므로, 방금 만든 review의 id를 바로 가져올 수 있다.
        createRequest.getImages().stream()
                .map(image -> createRequest.toReviewImage(createInfo.getId(), image))
                .forEach(reviewMapper::createImage);

        // DB에 리뷰 키워드 저장
        // review_keword 테이블에 추가
        createRequest.getKeywords().stream()
                .map(keywordId -> createRequest.toReviewKeyword(createInfo.getId(), keywordId))
                .forEach(reviewKewordMapper::createKeyword);

        // DB에 워크스페이스 키워드 저장
        // workspace_keyword 테이블에 추가
        createRequest.getKeywords().stream()
                .map(keywordId -> createRequest.toWorkspaceKeyword(createInfo.getWorkspaceId(), keywordId))
                .forEach(workspaceKeyword -> {
                    if(workspaceKeywordMapper.findByWorkspaceIdAndKeywordId(createInfo.getWorkspaceId(), workspaceKeyword.getKeywordId()) == 0)
                        workspaceKeywordMapper.createKeyword(workspaceKeyword);
                    else
                        workspaceKeywordMapper.increaseFrequency(workspaceKeyword.getWorkspaceId(), workspaceKeyword.getKeywordId());
                });
    }
}

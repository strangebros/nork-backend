package site.strangebros.nork.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.strangebros.nork.domain.review.entity.Review;
import site.strangebros.nork.domain.review.entity.ReviewImage;
import site.strangebros.nork.domain.review.entity.ReviewKeyword;
import site.strangebros.nork.domain.review.mapper.ReviewKewordMapper;
import site.strangebros.nork.domain.review.mapper.ReviewMapper;
import site.strangebros.nork.domain.review.service.dto.request.CreateRequest;
import site.strangebros.nork.domain.review.service.dto.request.ReadRequest;
import site.strangebros.nork.domain.review.service.dto.request.UpdateRequest;
import site.strangebros.nork.domain.review.service.dto.response.ReadResponse;
import site.strangebros.nork.domain.workspace.entity.WorkspaceKeyword;
import site.strangebros.nork.domain.workspace.mapper.VisitedSpaceMapper;
import site.strangebros.nork.domain.workspace.mapper.WorkspaceKeywordMapper;
import site.strangebros.nork.domain.workspace.mapper.WorkspaceMapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewMapper reviewMapper;
    private final ReviewKewordMapper reviewKewordMapper;
    private final WorkspaceMapper workspaceMapper;
    private final WorkspaceKeywordMapper workspaceKeywordMapper;
    private final VisitedSpaceMapper visitedSpaceMapper;

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

        // 워크스페이스에서 rating 과 number_of_visitors 수정
        workspaceMapper.updateRatingAndNumberOfVisitors(createInfo.getWorkspaceId(), createInfo.getRating());

        // visited_space에 최근 방문 일자 업데이트
        Integer visitedSpaceId = visitedSpaceMapper.findByMemberIdAndWorkspaceId(createInfo.getMemberId(), createInfo.getWorkspaceId());
        if( visitedSpaceId == null){
            visitedSpaceMapper.createVisitedSpace(createInfo.getMemberId(), createInfo.getWorkspaceId(), createInfo.getStartDatetime());
        }
        else{
            visitedSpaceMapper.updateVisitedSpace(visitedSpaceId, createInfo.getStartDatetime());
        }
    }

    // 리뷰 조회
    // 유저의 리뷰 5개씩 조회(단일 워크스페이스에 대하여) - 워크스페이스 페이지 -> 나의 리뷰 조회
    public List<ReadResponse> readWorkspaceReview(ReadRequest readRequest, Integer memberId) {
        // review entity로 변경
        Review readInfo = readRequest.toReview(memberId);

        // 검색
        int offset = (readRequest.getPage() - 1) * readRequest.getCount();
        List<Review> reviews = reviewMapper.findByMemberIdAndWorkspaceId(readInfo, offset, readRequest.getCount());

        // 가져온 리스트를 readResponse 리스트로 변환
        // 변환 시, image와 keyword는 List<String>, List<Integer>에서 List<ReadImageResponse>, List<ReadKeywordResponse> 로 바꿔야 함.
        return reviews.stream()
                .map(this::convertToReadResponse)
                .collect(Collectors.toList());
    }

    // 유저의 리뷰 10개씩 조회(모든 워크스페이스에 대하여) - 마이페이지 -> 리뷰들 조회
    public List<ReadResponse> readReview(ReadRequest readRequest, Integer memberId) {
        // review entity로 변경
        Review readInfo = readRequest.toReview(memberId);

        // 검색
        int offset = (readRequest.getPage() - 1) * readRequest.getCount();
        List<Review> reviews = reviewMapper.findByMemberId(readInfo, offset, readRequest.getCount());

        //가져온 리스트를 readResponse 리스트로 변환
        // 변환 시, image와 keyword는 List<String>, List<Integer>에서 List<ReadImageResponse>, List<ReadKeywordResponse> 로 바꿔야 함.
        return reviews.stream()
                .map(this::convertToReadResponse)
                .collect(Collectors.toList());
    }

    // 단일 리뷰 조회(업데이트 시 정보를 불러오기 위함)
    public ReadResponse readReviewDetail(int reviewId) {
        // 검색
        Review review = reviewMapper.findByReviewId(reviewId);

        // readResponse로 변환하여 반환
        // 변환 시, image와 keyword는 List<String>, List<Integer>에서 List<ReadImageResponse>, List<ReadKeywordResponse> 로 바꿔야 함.
        return convertToReadResponse(review);
    }

    // Review 객체를 ReadResponse로 변환
    public ReadResponse convertToReadResponse(Review review) {
        // 이미지 조회하여 이미지 리스트 만들기
        List<ReadResponse.ReadImageResponse> readImageResponseList = review.getImages().stream()
                .map(image -> {
                    ReviewImage reviewImage = reviewMapper.findImageByReviewIdAndImage(review.getId(), image);
                    return ReadResponse.ReadImageResponse.builder()
                            .visitedReviewImageId(reviewImage.getId())
                            .image(image)
                            .build();
                })
                .collect(Collectors.toList());

        // 키워드 조회하여 키워드 리스트 만들기
        List<ReadResponse.ReadKeywordResponse> readKeywordResponseList = review.getKeywords().stream()
                .map(keywordId -> {
                    ReviewKeyword reviewKeyword = reviewKewordMapper.findKeywordByReviewIdAndKeyword(review.getId(), keywordId);
                    return ReadResponse.ReadKeywordResponse.builder()
                            .reviewKeywordId(reviewKeyword.getId())
                            .value(reviewKewordMapper.findKeywordValueByKeywordId(keywordId))
                            .build();
                })
                .collect(Collectors.toList());

        return ReadResponse.builder()
                .id(review.getId())
                .memberId(review.getMemberId())
                .workspaceId(review.getWorkspaceId())
                .startDatetime(review.getStartDatetime())
                .endDatetime(review.getEndDatetime())
                .activity(review.getActivity())
                .rating(review.getRating())
                .reviewText(review.getReviewText())
                .images(readImageResponseList)
                .keywords(readKeywordResponseList)
                .build();
    }

    // 리뷰 업데이트
    public void updateReview(int reviewId, UpdateRequest updateRequest) {
        // Review Entity로 변경
        Review updateInfo = updateRequest.toReview(reviewId);

        // DB에 저장
        reviewMapper.update(updateInfo);
    }
}

package edu.bookreview.service;

import edu.bookreview.entity.BookReview;
import edu.bookreview.entity.LikeBookReview;
import edu.bookreview.entity.User;
import edu.bookreview.repository.BookReviewRepository;
import edu.bookreview.repository.LikeBookReviewRepository;
import edu.bookreview.security.PrincipalDetails;
import edu.bookreview.util.UpdateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {

    private final BookReviewRepository bookReviewRepository;
    private final LikeBookReviewRepository likeBookReviewRepository;
    private final UpdateUtil updateUtil;

    // 좋아요 기능 추가
    // 로그인한 유저가 해당 게시글에 좋아요를 추가할 때 해당 게시글에 좋아요를 한 적이 있으면 return false
    // 처음 좋아요를 하는 경우 해당 게시글의 likeCount + 1, retrun true
    @Transactional
    public boolean likeBookReview(PrincipalDetails principalDetails, Long review_id) {

        LikeBookReview likeBookReview = likeBookReviewRepository.findByUserIdAndBookReviewId(principalDetails.getUser().getId(), review_id).orElse(null);
        BookReview bookReview = bookReviewRepository.findById(review_id).orElseThrow(() -> new IllegalAccessError("Review is not exist"));
        int totalLikeCnt = 0;

        // Dto 를 사용하지 않고 entity로 데이터를 수정해줘도 되는건가?
        if (likeBookReview == null) {
            LikeBookReview firstLike = LikeBookReview.builder()
                    .user(principalDetails.getUser())
                    .bookReview(bookReview)
                    .likeStatus(false)   // true : 좋아요 안누른 상태, false : 좋아요 누른 상태
                    .build();
            likeBookReviewRepository.save(firstLike);
            bookReview.addBookReview(firstLike);
            totalLikeCnt = bookReview.getLikeCount() + 1;

            updateUtil.updateLikeCnt(review_id, totalLikeCnt);
            return true;
        }
        return false;
    }

    public boolean getStatus(User user, Long reviewId){
        LikeBookReview likeBookReview = likeBookReviewRepository.findByUserIdAndBookReviewId(user.getId(), reviewId).orElse(null);
        return likeBookReview == null; //ture : 추천 가능, false : 추천 불가능
    }
}
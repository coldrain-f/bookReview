package edu.bookreview.util;

import edu.bookreview.repository.BookReviewRepository;
import edu.bookreview.repository.LikeBookReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUtil {

    // TODO: 2022/06/13
    private final BookReviewRepository bookReviewRepository;

    private final LikeBookReviewRepository likeBookReviewRepository;

    // 게시글 좋아요 수 업데이트
    public void updateLikeCnt(Long bookReviewId, Integer totalLikeCnt){
        bookReviewRepository.updateLikeCnt(bookReviewId, totalLikeCnt);
    }

    // 게시글에 따른 유저의 좋아요 상태 업데이트
    public void updateLikeStatus(Long likeBookReviewId, boolean status){
        likeBookReviewRepository.updateLikeStatus(likeBookReviewId, status);
    }
}

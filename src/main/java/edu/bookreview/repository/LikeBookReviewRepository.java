package edu.bookreview.repository;

import edu.bookreview.entity.BookReview;
import edu.bookreview.entity.LikeBookReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeBookReviewRepository extends JpaRepository<LikeBookReview, Long> {

    @Query("select lbr from LikeBookReview lbr where lbr.bookReview.id = :bookReviewId")
    List<LikeBookReview> searchLikeBookReview(Long bookReviewId);

    // 해당 게시글에 유저가 좋아요를 눌렀는지 확인
    Optional<LikeBookReview> findByUserIdAndBookReviewId(Long userId, Long bookReviewId);

    // TODO: 2022-06-14 @Param 지우고 테스트 작업 필요
    @Modifying
    @Query("update LikeBookReview lbr set lbr.likeStatus = :likeStatus where lbr.id = :likeBookReviewId")
    void updateLikeStatus(Long likeBookReviewId, boolean likeStatus);
}

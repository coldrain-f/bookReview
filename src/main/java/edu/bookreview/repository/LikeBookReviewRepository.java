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

    @Query("select lbookreview from LikeBookReview lbookreview where lbookreview.bookReview.id = :BOOK_REVIEW_ID")
    List<LikeBookReview> searchLikeBookReview(Long BOOK_REVIEW_ID);

    Optional<LikeBookReview> findByUserIdAndBookReviewId(Long userId, Long bookReviewId); // 해당 게시글에 유저가 좋아요를 눌렀는지 확인

    @Modifying
    @Query("update LikeBookReview l set l.likeCheck = :likeCheck where l.id = :id")
    void updateLikeStatus(@Param(value = "id") Long likeBookReviewId, @Param(value = "likeCheck") boolean likeStatus);
}

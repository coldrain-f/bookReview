package edu.bookreview.repository;

import edu.bookreview.entity.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookReviewRepository extends JpaRepository<BookReview, Long>{

    @Modifying
    @Query("update BookReview b set b.likeCount = :likeCount where b.id = :id")
    void updateLikeCnt(@Param(value = "id") Long bookReviewId, @Param(value = "likeCount") Integer totalLikeCnt);
}

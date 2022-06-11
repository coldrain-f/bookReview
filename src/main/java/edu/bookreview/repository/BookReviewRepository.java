package edu.bookreview.repository;

import edu.bookreview.entity.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReviewRepository extends JpaRepository<BookReview, Long> {
    List<BookReview> findAllByOrderByCreatedDateDesc();
}

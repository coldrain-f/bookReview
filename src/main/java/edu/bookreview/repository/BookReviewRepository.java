package edu.bookreview.repository;

import edu.bookreview.dto.BookReviewDto;
import edu.bookreview.entity.BookReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookReviewRepository extends JpaRepository<BookReview, Long> {
    List<BookReview> findAllByOrderByCreatedDateDesc();

    Page<BookReview> findAll(Pageable pageable);
}

package edu.bookreview.service;

import edu.bookreview.aws.AwsS3FileUploader;
import edu.bookreview.entity.BookReview;
import edu.bookreview.repository.BookReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final BookReviewRepository bookReviewRepository;

    private final AwsS3FileUploader awsS3FileUploader;

    public Optional<BookReview> getReview(Long id) {
        return bookReviewRepository.findById(id);
    }
//    public Page<Review> getReviews(Pageable pageable) {
//        return reviewRepository.findAll(pageable);
//    }

    public Page<BookReview> getReviews(Pageable pageable) {
        return bookReviewRepository.findAll(pageable);
    }
}

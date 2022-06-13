package edu.bookreview.controller;

import edu.bookreview.dto.ReviewCreateRequest;
import edu.bookreview.dto.ReviewDetail;
import edu.bookreview.entity.Review;
import edu.bookreview.security.PrincipalDetails;
import edu.bookreview.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/reviews")
    public Page<ReviewDetail> getReviews(
            @PageableDefault(size = 5, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Review> reviewPage = reviewService.getReviews(pageable);
        return reviewPage.map(ReviewDetail::from);
    }

    @GetMapping("/reviews/{id}")
    public ReviewDetail getReview(@PathVariable Long id) {
        Review review = reviewService.getReview(id)
                .orElseThrow(() -> new IllegalArgumentException("review is not found."));

        return ReviewDetail.from(review);
    }


    @PostMapping("/reviews")
    public void createReview(ReviewCreateRequest reviewCreateRequest,
                             @AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("request = {}", reviewCreateRequest);
        reviewService.writeReview(principalDetails.getUser(), reviewCreateRequest);
    }

}

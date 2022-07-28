package edu.bookreview.controller;

import edu.bookreview.dto.ReviewDetail;
import edu.bookreview.dto.ReviewEditRequestDto;
import edu.bookreview.dto.ReviewsRequestDto;
import edu.bookreview.entity.BookReview;
import edu.bookreview.security.PrincipalDetails;
import edu.bookreview.service.BookReviewService;
import edu.bookreview.service.LikeService;
import edu.bookreview.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookReviewController {

    private final BookReviewService bookReviewService;
    private final LikeService likeService;
    private final ReviewService reviewService;

    @GetMapping("/bookreviews")
    public Page<ReviewDetail> getReviews(
            @PageableDefault(size = 5, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<BookReview> reviewPage = reviewService.getReviews(pageable);
        return reviewPage.map(ReviewDetail::from);
    }

    @GetMapping("/bookreviews/{id}")
    public ReviewDetail getReview(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long id) {
        BookReview review = reviewService.getReview(id)
                .orElseThrow(() -> new IllegalArgumentException("review is not found."));
        if (principalDetails == null) {
            return ReviewDetail.from(review, false);
        }
        boolean userLikeStatus = likeService.getStatus(principalDetails.getUser(), id);
        return ReviewDetail.from(review, userLikeStatus);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/bookreviews")
    public void writeBookReview(@AuthenticationPrincipal PrincipalDetails principalDetails
            , ReviewsRequestDto reviewsRequestDto) {
        bookReviewService.writeBookReview(reviewsRequestDto.toEntity(principalDetails.getUser()), reviewsRequestDto.getFile());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @PatchMapping("/bookreviews/{id}")
    public void editBookReview(@AuthenticationPrincipal PrincipalDetails principalDetails
            , @PathVariable Long id
            , @RequestBody ReviewEditRequestDto reviewEditRequestDto) {
        bookReviewService.editBookReview(principalDetails, id, reviewEditRequestDto);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @DeleteMapping("/bookreviews/{id}")
    public void deleteBookReview(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long id) {
        bookReviewService.deleteBookReview(principalDetails, id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @PostMapping("/bookreviews/{id}/like")
    public boolean likeBookReview(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long id) {
        return likeService.likeBookReview(principalDetails, id);
    }
}
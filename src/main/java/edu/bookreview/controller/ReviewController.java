package edu.bookreview.controller;

import edu.bookreview.dto.ReviewCreateRequest;
import edu.bookreview.security.PrincipalDetails;
import edu.bookreview.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/reviews")
    public void createReview(ReviewCreateRequest reviewCreateRequest,
                             @AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("request = {}", reviewCreateRequest);
        reviewService.writeReview(principalDetails.getUser(), reviewCreateRequest);
    }

}

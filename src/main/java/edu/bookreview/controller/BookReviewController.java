package edu.bookreview.controller;

import edu.bookreview.dto.BookReviewDto;
import edu.bookreview.security.PrincipalDetails;
import edu.bookreview.service.BookReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BookReviewController {

    private final BookReviewService bookReviewService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/api/bookreviews")
    public void writeBookReview(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody BookReviewDto bookReviewDto){
        bookReviewService.writeBookReview(principalDetails, bookReviewDto);
    }
}

package edu.bookreview.controller;

import edu.bookreview.dto.DetailPageDTO;
import edu.bookreview.service.BookReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DetailController {
    private final BookReviewService bookReviewService;

    @GetMapping("/api/bookreviews/{id}")
    public DetailPageDTO findBookReview(@PathVariable Long id){
        return bookReviewService.findBookReview(id);
    }
}
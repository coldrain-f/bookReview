package edu.bookreview.controller;

import edu.bookreview.dto.MainPageDTO;
import edu.bookreview.repository.BookReviewRepository;
import edu.bookreview.service.BookReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final BookReviewService bookReviewService;
    private final BookReviewRepository bookReviewRepository;

    @GetMapping("/api/bookreviews")
    public List<MainPageDTO> allBookReviews(){
        return bookReviewService.getAllBookReviews();
    }
}
package edu.bookreview.service;

import edu.bookreview.repository.BookReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class recommandationService {

    private final BookReviewRepository bookReviewRepository;
}

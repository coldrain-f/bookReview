package edu.bookreview.service;

import edu.bookreview.dto.DetailPageDTO;
import edu.bookreview.dto.MainPageDTO;
import edu.bookreview.entity.BookReview;
import edu.bookreview.entity.Timestamped;
import edu.bookreview.repository.BookReviewRepository;
import edu.bookreview.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Getter
@RequiredArgsConstructor
public class BookReviewService extends Timestamped {
    private final BookReviewRepository bookReviewRepository;
    private final UserRepository userRepository;

    public List<MainPageDTO> getAllBookReviews() {
        List<BookReview> bookReviews = bookReviewRepository.findAllByOrderByCreatedDateDesc();
        List<MainPageDTO> mainPageDTOS = new ArrayList<>();

        for (BookReview bookReview: bookReviews){
            MainPageDTO mainPageDTO = new MainPageDTO();
            mainPageDTOS.add(mainPageDTO);
        }
        return mainPageDTOS;
    }

    @Transactional
    public DetailPageDTO findBookReview(Long id){
        BookReview bookReview = bookReviewRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));
        return new DetailPageDTO();
    }
}

package edu.bookreview.service;

import org.springframework.stereotype.Service;

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

package edu.bookreview.service;

import edu.bookreview.dto.BookReviewDto;
import edu.bookreview.entity.BookReview;
import edu.bookreview.repository.BookReviewRepository;
import edu.bookreview.security.PrincipalDetails;
import edu.bookreview.util.SaveFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookReviewService {

    private final BookReviewRepository bookReviewRepository;
    private final SaveFile saveFile;


    @Transactional
    public void writeBookReview(PrincipalDetails principalDetails, BookReviewDto bookReviewDto, MultipartFile imgFile) {

        String bookImageUrl = saveFile.saveFile(imgFile);

        // BookReview entity 생성
        BookReview bookReview = BookReview.builder()
                .user(principalDetails.getUser())
                .title(bookReviewDto.getTitle())
                .bookBuyUrl(bookReviewDto.getBookBuyUrl())
                .bookImageUrl(bookImageUrl)
                .content(bookReviewDto.getContent())
                .rank(bookReviewDto.getRank())
                .build();

        bookReviewRepository.save(bookReview);
    }
//@Transactional
//public void writeBookReview(BookReviewDto bookReviewDto, MultipartFile imgFile) {
//
//    String bookImageUrl = saveFile.saveFile(imgFile);
//    User user = User.builder()
//            .username("ulaf")
//            .nickname("ulaf")
//            .password("fasdfsaf")
//            .build();
//
//    // BookReview entity 생성
//    BookReview bookReview = BookReview.builder()
//            .user(user)
//            .title(bookReviewDto.getTitle())
//            .bookBuyUrl(bookReviewDto.getBookBuyUrl())
//            .bookImageUrl(bookImageUrl)
//            .content(bookReviewDto.getContent())
//            .rank(bookReviewDto.getRank())
//            .build();
//
//    bookReviewRepository.save(bookReview);
//    }
}

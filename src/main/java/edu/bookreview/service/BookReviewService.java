package edu.bookreview.service;

import edu.bookreview.dto.DetailPageDTO;
import edu.bookreview.dto.MainPageDTO;
import edu.bookreview.dto.BookReviewDto;
import edu.bookreview.entity.BookReview;
import edu.bookreview.entity.Timestamped;
import edu.bookreview.repository.BookReviewRepository;
import edu.bookreview.security.PrincipalDetails;
import edu.bookreview.repository.UserRepository;
import edu.bookreview.util.SaveFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.Getter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Getter
@RequiredArgsConstructor
public class BookReviewService extends Timestamped {
    private final BookReviewRepository bookReviewRepository;
    private final UserRepository userRepository;
    private final SaveFile saveFile;

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
}

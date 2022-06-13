package edu.bookreview.service;

import edu.bookreview.dto.BookReviewDto;
import edu.bookreview.dto.DetailPageDto;
import edu.bookreview.dto.MainPageDto;
import edu.bookreview.entity.BookReview;
import edu.bookreview.entity.Timestamped;
import edu.bookreview.repository.BookReviewRepository;
import edu.bookreview.repository.UserRepository;
import edu.bookreview.security.PrincipalDetails;
import edu.bookreview.util.SaveFile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Transactional
    public List<MainPageDto> getAllBookReviews() {
        List<BookReview> bookReviews = bookReviewRepository.findAllByOrderByCreatedDateDesc();
        List<MainPageDto> mainPageDtos = new ArrayList<>();

        for (BookReview bookReview: bookReviews){
            MainPageDto mainPageDto = new MainPageDto();
            mainPageDtos.add(mainPageDto);
        }
        return mainPageDtos;
    }

    public Page<BookReview> viewAll(Pageable pageable) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction);
        pageable = PageRequest.of(0, 10, sort);
        return bookReviewRepository.findAll(pageable);
    }

    @Transactional
    public DetailPageDto findBookReview(Long id){
        BookReview bookReview = bookReviewRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));
        return new DetailPageDto();
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

    @Transactional
    public void writeBookReviewTest(PrincipalDetails principalDetails, BookReviewDto bookReviewDto){
        BookReview bookReview = BookReview.builder()
                .user(principalDetails.getUser())
                .title(bookReviewDto.getTitle())
                .bookBuyUrl(bookReviewDto.getBookBuyUrl())
                .bookImageUrl(bookReviewDto.getImgUrl())
                .content(bookReviewDto.getContent())
                .rank(bookReviewDto.getRank())
                .build();
        bookReviewRepository.save(bookReview);
    }
}

package edu.bookreview.service;

import edu.bookreview.dto.BookReviewDto;
import edu.bookreview.dto.ImgFileDto;
import edu.bookreview.dto.ReviewsRequestDto;
import edu.bookreview.entity.BookReview;
import edu.bookreview.repository.BookReviewRepository;
import edu.bookreview.security.PrincipalDetails;
import edu.bookreview.util.SaveFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookReviewService {

    private final BookReviewRepository bookReviewRepository;
    private final SaveFile saveFile;

    public void writeBookReview(PrincipalDetails principalDetails, BookReview bookReview, MultipartFile file) {

        String bookImageUrl = saveFile.saveFile(file);

        // BookReview entity 생성
        bookReview.addImgUrl(bookImageUrl);

        bookReviewRepository.save(bookReview);
    }
}

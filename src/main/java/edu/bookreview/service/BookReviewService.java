package edu.bookreview.service;

import edu.bookreview.dto.BookReviewDto;
import edu.bookreview.dto.ReviewEditRequestDto;
import edu.bookreview.entity.BookReview;
import edu.bookreview.entity.User;
import edu.bookreview.repository.BookReviewRepository;
import edu.bookreview.security.PrincipalDetails;
import edu.bookreview.util.S3Util;
import edu.bookreview.util.SaveFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookReviewService {

    @PersistenceContext
    private EntityManager em;
    private final BookReviewRepository bookReviewRepository;
    private final S3Util s3Util;

    public void writeBookReview(BookReview bookReview, MultipartFile file) {

        String bookImageUrl = s3Util.S3Uploader(file);

        // BookReview entity 생성
        bookReview.addImgUrl(bookImageUrl);

        bookReviewRepository.save(bookReview);
    }

    // TODO: 2022/06/14
    // 수정 기능 작성
    @Transactional
    public String editBookReview(PrincipalDetails principalDetails, Long reviewid, ReviewEditRequestDto reviewEditRequestDto) {
        BookReview bookReview = bookReviewRepository.findByIdAndUser(reviewid, principalDetails.getUser())
                .orElseThrow(() -> new NullPointerException("You are not the author of this post"));

        bookReview.updateBookReview(bookReview.getUser()
                , reviewEditRequestDto.getTitle()
                , reviewEditRequestDto.getBookBuyUrl()
                , bookReview.getBookImageUrl()
                , reviewEditRequestDto.getContent()
                , bookReview.getLikeCount()
                , reviewEditRequestDto.getRank()
                );

        // findBy 로 영속성 컨텍스트에 올린 후 변경이 발생하면 쓰기 지연 저장소에 저장된 entity가 @Transactional 에 의해 메소드가 끝난 후 commit이 일어난다
        //bookReviewRepository.save(bookReview);
        return "edit complete";
    }

    @Transactional
    public void deleteBookReview(PrincipalDetails principalDetails, Long bookReviewId) {
        BookReview bookReview = bookReviewRepository.findByIdAndUser(bookReviewId, principalDetails.getUser()).orElseThrow(
                () -> new NullPointerException("You are not the author of this post")
        );

        s3Util.deleteS3File(bookReview.getBookImageUrl());
        bookReviewRepository.delete(bookReview);
    }
}

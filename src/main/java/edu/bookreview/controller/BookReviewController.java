package edu.bookreview.controller;

import edu.bookreview.dto.BookReviewDto;
import edu.bookreview.security.PrincipalDetails;
import edu.bookreview.service.BookReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BookReviewController {

    private final BookReviewService bookReviewService;

    // Multipart requests consist of sending data of many different types separated by a boundary as part of a single HTTP method call.
    // @RequestPart : method argument 와 함께 요청되는 multipart request를 관리하는 어노테이션
    // @RequestPart(value = "file", required = false) :
    // required = true (default) 경우
    // 쿼리 스트링에 MultipartFile imgFile 이 없을 경우, 즉 /api/bookreviews 와 같이 @RequsetPart가 적용된 필드가 없으면
    // Bad Request, Required MultipartFile parameter 'imgFile' is not present 라는 예외를 발생
    // required = false 주의할 점
    // Spring이 해당 Argument 를 무시한다.

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/api/bookreviews")
    public void writeBookReview(@AuthenticationPrincipal PrincipalDetails principalDetails
            , @RequestPart BookReviewDto bookReviewDto
            , @RequestPart(value = "file", required = false) MultipartFile imgFile){
        bookReviewService.writeBookReview(principalDetails, bookReviewDto, imgFile);
    }
}

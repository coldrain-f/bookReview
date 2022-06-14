package edu.bookreview.controller;

import edu.bookreview.dto.ReviewEditRequestDto;
import edu.bookreview.dto.ReviewsRequestDto;
import edu.bookreview.security.PrincipalDetails;
import edu.bookreview.service.BookReviewService;
import edu.bookreview.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Slf4j
@ToString
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookReviewController {

    private final BookReviewService bookReviewService;
    private final LikeService likeService;


    // Multipart requests consist of sending data of many different types separated by a boundary as part of a single HTTP method call.
    // @RequestPart : method argument 와 함께 요청되는 multipart request를 관리하는 어노테이션
    // @RequestPart(value = "file", required = false) :
    // required = true (default) 경우
    // 쿼리 스트링에 MultipartFile imgFile 이 없을 경우, 즉 /api/bookreviews 와 같이 @RequsetPart가 적용된 필드가 없으면
    // Bad Request, Required MultipartFile parameter 'imgFile' is not present 라는 예외를 발생
    // required = false 주의할 점
    // Spring이 해당 Argument 를 무시한다.

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/bookreviews")
    public void writeBookReview(@AuthenticationPrincipal PrincipalDetails principalDetails
            , ReviewsRequestDto reviewsRequestDto) {
        bookReviewService.writeBookReview(reviewsRequestDto.toEntity(principalDetails.getUser()), reviewsRequestDto.getFile());
    }

    // TODO: 2022/06/14
    // 수정 기능 작성
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping ("/bookreviews/{id}")
    public ResponseEntity<String> editBookReview(@AuthenticationPrincipal PrincipalDetails principalDetails
            , @PathVariable Long id
            , @RequestBody ReviewEditRequestDto reviewEditRequestDto){
        HttpHeaders headers = new HttpHeaders();
        String msg = bookReviewService.editBookReview(principalDetails, id, reviewEditRequestDto);
        return new ResponseEntity<String>(msg, headers, HttpStatus.valueOf(200));
    }


    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/bookreviews/{id}")
    public void deleteBookReview(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long id){
        bookReviewService.deleteBookReview(principalDetails, id);
    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/bookreviews/{id}/like")
    public boolean likeBookReview(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long id){
        return likeService.likeBookReview(principalDetails, id);
    }
}
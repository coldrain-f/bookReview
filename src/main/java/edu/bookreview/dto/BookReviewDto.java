package edu.bookreview.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode  // equals : 두 객체의 내용이 같은지 동등성(equality), hashCode : 두 객체가 같은 객체인지, 동일성(identity)를 비교하는 연산자
public class BookReviewDto {

    private final String nickname;
    private final Integer rank;
    private final String title;
    private final String bookBuyUrl;
    // spring 에서 사용하는 파일 타입
//    private final MultipartFile file;
    private final String imgUrl;
    private final String content;
    private final Integer likeCount;

    @Builder
    public BookReviewDto(String nickname, Integer rank, String title, String bookBuyUrl, String imgUrl, String content, Integer likeCount){
        this.nickname = nickname;
        this.rank = rank;
        this.title = title;
        this.bookBuyUrl = bookBuyUrl;
        this.imgUrl = imgUrl;
        this.content = content;
        this.likeCount = likeCount;
    }
}

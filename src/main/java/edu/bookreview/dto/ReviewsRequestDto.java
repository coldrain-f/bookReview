package edu.bookreview.dto;

import edu.bookreview.entity.BookReview;
import edu.bookreview.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@ToString
public class ReviewsRequestDto {

    private final String nickname;
    private final Integer rank;
    private final String title;
    private final String bookBuyUrl;
    // spring 에서 사용하는 파일 타입
    private final MultipartFile file;
    private final String content;

    @Builder
    public ReviewsRequestDto(String nickname, Integer rank, String title, String bookBuyUrl, MultipartFile file, String content){
        this.nickname = nickname;
        this.rank = rank;
        this.title = title;
        this.bookBuyUrl = bookBuyUrl;
        this.file = file;
        this.content = content;
    }

    public BookReview toEntity(User user){
        return BookReview.builder()
                .user(user)
                .title(this.title)
                .rank(this.rank)
                .bookBuyUrl(this.bookBuyUrl)
                .content(this.content)
                .build();
    }
}


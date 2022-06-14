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
    private final String bookBuyUrl;
    private final Integer rank;
    private final MultipartFile file;
    private final String title;
    private final String content;

    @Builder
    public ReviewsRequestDto(Integer rank, String title, String bookBuyUrl, MultipartFile file, String content){
        this.bookBuyUrl = bookBuyUrl;
        this.rank = rank;
        this.file = file;
        this.title = title;
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


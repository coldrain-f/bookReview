package edu.bookreview.dto;

import edu.bookreview.entity.BookReview;
import edu.bookreview.entity.Timestamped;
import edu.bookreview.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MainPageDto extends Timestamped {
    private Long id;

    private User nickname;

    private String bookImageUrl;

    private String title;

    private String content;

    private Integer likeCount;

    private LocalDateTime createdDate;

    public MainPageDto(BookReview bookReview, LocalDateTime createdDate) {
        this.id = bookReview.getId();
        this.nickname = bookReview.getUser();
        this.bookImageUrl = bookReview.getBookImageUrl();
        this.title = bookReview.getBookImageUrl();
        this.content = bookReview.getContent();
        this.likeCount = bookReview.getLikeCount();
        this.createdDate = getCreatedDate();
    }
}
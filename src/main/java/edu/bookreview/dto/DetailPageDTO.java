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
public class DetailPageDTO extends Timestamped {
    private Long id;

    private User nickname;

    private Integer rank;

    private String bookBuyUrl;

    private String bookImageUrl;

    private String title;

    private String content;

    private Integer likeCount;

    private LocalDateTime createdDate;

    public DetailPageDTO(BookReview bookReview, String bookBuyUrl, LocalDateTime createdDate) {
        this.id = bookReview.getId();
        this.nickname = bookReview.getUser();
        this.rank = bookReview.getRank();
        this.bookBuyUrl = getBookBuyUrl();
        this.bookImageUrl = bookReview.getBookImageUrl();
        this.title = bookReview.getBookImageUrl();
        this.content = bookReview.getContent();
        this.likeCount = bookReview.getLikeCount();
        this.createdDate = getCreatedDate();
    }
}

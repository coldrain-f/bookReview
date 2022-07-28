package edu.bookreview.dto;

import edu.bookreview.entity.BookReview;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReviewEditRequestDto {

    private final String bookBuyUrl;
    private final Integer rank;
    private final String title;
    private final String content;

    public BookReview toEntity(BookReview bookReview){
        return BookReview.builder()
                .user(bookReview.getUser())
                .bookBuyUrl(this.bookBuyUrl)
                .title(this.title)
                .content(this.content)
                .bookImageUrl(bookReview.getBookImageUrl())
                .likeCount(bookReview.getLikeCount())
                .rank(this.rank)
                .build();
    }
}

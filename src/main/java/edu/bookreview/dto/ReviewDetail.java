package edu.bookreview.dto;

import edu.bookreview.entity.BookReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ReviewDetail {

    private Long id;
    private String nickname;
    private Integer rank;
    private String bookBuyUrl;
    private String bookImageUrl;
    private String title;
    private String content;
    private Integer like;
    private boolean userLikeStatus;
    private LocalDateTime createdDate;


    public static ReviewDetail from(BookReview review) {
        return ReviewDetail.builder()
                .id(review.getId())
                .nickname(review.getUser().getNickname())
                .rank(review.getRank())
                .bookBuyUrl(review.getBookBuyUrl())
                .bookImageUrl(review.getBookImageUrl())
                .title(review.getTitle())
                .content(review.getContent())
                .like(review.getLikeCount())
                .createdDate(review.getCreatedDate())
                .build();
    }


    public static ReviewDetail from(BookReview review, boolean userLikeStatus) {
        return ReviewDetail.builder()
                .id(review.getId())
                .nickname(review.getUser().getNickname())
                .rank(review.getRank())
                .bookBuyUrl(review.getBookBuyUrl())
                .bookImageUrl(review.getBookImageUrl())
                .title(review.getTitle())
                .content(review.getContent())
                .like(review.getLikeCount())
                .userLikeStatus(userLikeStatus)
                .createdDate(review.getCreatedDate())
                .build();
    }
}
package edu.bookreview.dto;

import edu.bookreview.entity.Review;
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
    private LocalDateTime createdDate;

    public static ReviewDetail from(Review review) {
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
}
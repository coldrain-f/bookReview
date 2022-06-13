package edu.bookreview.dto;

import edu.bookreview.entity.Review;
import edu.bookreview.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class ReviewCreateRequest {

    private String bookBuyUrl;
    private Integer rank;
    private MultipartFile file;
    private String title;
    private String content;

    public Review toEntity(User user, String bookImageUrl) {
        return Review.builder()
                .bookImageUrl(bookImageUrl)
                .bookBuyUrl(this.bookBuyUrl)
                .rank(this.rank)
                .title(this.title)
                .content(this.content)
                .user(user)
                .build();
    }
}

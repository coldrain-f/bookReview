package edu.bookreview.entity;

import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.*;

@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(nullable = false)
    private String title;

    private String bookImageUrl;

    private String bookBuyUrl;

    private String content;

    // like DB 예약어.
    private Integer likeCount;

    @Column(nullable = false)
    private Integer rank;

    @Builder
    public Review(User user, String title, String bookImageUrl, String bookBuyUrl, String content, Integer rank) {
        Assert.hasText(title, "title must not be empty");
        if (user == null) throw new IllegalArgumentException("user is null");
        if (rank == null) throw new IllegalArgumentException("rank is null");

        this.user = user;
        this.title = title;
        this.bookImageUrl = bookImageUrl;
        this.bookBuyUrl = bookBuyUrl;
        this.content = content;
        this.rank = rank;
    }

    public void setBookImageUrl(String bookImageUrl) {
        this.bookImageUrl = bookImageUrl;
    }

    @PrePersist // 영속화 되기전 실행.
    public void initDefaultLikeCount() {
        this.likeCount = this.likeCount == null ? 0 : this.likeCount;
    }
}

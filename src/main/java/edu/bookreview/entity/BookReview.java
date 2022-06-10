package edu.bookreview.entity;

import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class BookReview extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOK_REVIEW_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(nullable = false)
    private String title;

    private String bookImageUrl;

    // 값이 비어있어도 괜찮은지?
    private String content;

    // like 데이터베이스 예약어.
    private Integer likeCount;

    @Column(nullable = false)
    private Integer rank;

    @Builder
    public BookReview(User user, String title, String bookImageUrl, String content, Integer rank) {
        Assert.hasText(title, "title must not be empty");
        if (user == null) throw new IllegalArgumentException("user is null");
        if (rank == null) throw new IllegalArgumentException("rank is null");

        this.user = user;
        this.title = title;
        this.bookImageUrl = bookImageUrl;
        this.content = content;
        this.rank = rank;
    }

    @PrePersist // 영속화 되기전에 실행.
    public void initDefaultLikeCount() {
        this.likeCount = this.likeCount == null ? 0 : this.likeCount;
    }
}

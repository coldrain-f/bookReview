package edu.bookreview.entity;

import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"id"})
public class BookReview extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOK_REVIEW_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String bookBuyUrl;

    // 사진을 전송받아서 그 사진을 서버의 특정 폴더에 저장
    // DB의 경로를 저장
    @Column
    private String bookImageUrl;

    // 값이 비어있어도 괜찮은지?
    @Column(nullable = false)
    private String content;

    // like 데이터베이스 예약어.
    @Column
    private Integer likeCount;

    @Column(nullable = false)
    private Integer rank;

    @OneToMany(mappedBy = "bookReview")
    private List<LikeBookReview> likeBookReviews = new ArrayList<>();

    @Builder
    public BookReview(User user, String title, String bookBuyUrl, String bookImageUrl, String content, Integer rank) {
        Assert.hasText(title, "title must not be empty");
        if (user == null) throw new IllegalArgumentException("user is null");
        if (rank == null) throw new IllegalArgumentException("rank is null");

        this.user = user;
        this.title = title;
        this.bookBuyUrl = bookBuyUrl;
        this.bookImageUrl = bookImageUrl;
        this.content = content;
        this.rank = rank;
    }

    public void addBookReview(LikeBookReview likeBookReview){
        likeBookReviews.add(likeBookReview);
        likeBookReview.addBookReview(this);
    }

    public void addImgUrl(String bookImageUrl){
        this.bookImageUrl = bookImageUrl;
    }
    public void updateLikeCnt(Integer likeCount){
        this.likeCount = likeCount;
    }

    @PrePersist // 영속화 되기전에 실행.
    public void initDefaultLikeCount() {
        this.likeCount = this.likeCount == null ? 0 : this.likeCount;
    }
}

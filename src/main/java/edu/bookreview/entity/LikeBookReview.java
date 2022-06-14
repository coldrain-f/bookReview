package edu.bookreview.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"id"})
public class LikeBookReview extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_BOOKREVIEW_ID")
    private Long id;

    // 양방향 매핑시 순환참조 방지
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOOK_REVIEW_ID")
    private BookReview bookReview;


    @Column(name = "LIKE_STATUS")
    private Boolean likeStatus; // true 일 때가 좋아요를 하지 않은 상태

    @Builder
    public LikeBookReview(User user, BookReview bookReview, boolean likeStatus){
        this.user = user;
        this.bookReview = bookReview;
        this.likeStatus = likeStatus;
    }

    public void addBookReview(BookReview bookReview){
        this.bookReview = bookReview;
    }

//    public void changStatus()
    // 영속화 되기전에 실행
    @PersistenceContext
    public void initLikeStatus() {
        if(this.likeStatus) this.likeStatus = true;
    }
}

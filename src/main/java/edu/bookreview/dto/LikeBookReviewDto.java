package edu.bookreview.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeBookReviewDto {

    // boolean (primitive) 기본값 : false , Boolean (primitive wrapper) 기본값 : null
    private boolean likeCheck;

    public void changeStatus(boolean likeCheck){
        this.likeCheck = likeCheck;
    }
}

package edu.bookreview.security.jwt;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VerifyResult {

    private boolean success;
    private String username;

    @Builder
    public VerifyResult(boolean success, String username) {
        this.success = success;
        this.username = username;
    }
}

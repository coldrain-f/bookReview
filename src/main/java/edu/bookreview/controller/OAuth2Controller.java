package edu.bookreview.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * OAuth2 동작 확인용 컨트롤러
 */
@RestController
public class OAuth2Controller {

    // OAuth2 사용자 정보를 받아보려면 OAuth2User 객체를 받으면 된다.
    @GetMapping("/greeting")
    public OAuth2User greeting(@AuthenticationPrincipal OAuth2User oAuth2User) {
        return oAuth2User;
    }
}

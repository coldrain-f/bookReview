package edu.bookreview.controller;

import edu.bookreview.entity.User;
import edu.bookreview.service.UserService;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    @PostMapping("/api/signup")
    public void signup(@RequestBody @Valid AuthUserRequest authUserRequest) {
        User user = authUserRequest.toEntity(bCryptPasswordEncoder);
        userService.signup(user);
    }

    @GetMapping(value = "/api/authentication")
    public Authentication authentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Getter
    private static class AuthUserRequest {

        @NotBlank
        @Pattern(regexp = "^[a-z0-9_-]{5,20}$", message = "5~20 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능")
        private final String username;

        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9가-힣]{2,10}$", message = "2~10 숫자, 영어, 한글만 사용 가능합니다.")
        private final String nickname;

        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+~,.?:{}|<>\\[\\]]{8,16}$", message = "8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요")
        private final String password;

        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+~,.?:{}|<>\\[\\]]{8,16}$", message = "8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요")
        private final String confirmPassword;


        @Builder
        public AuthUserRequest(String username, String nickname, String password, String confirmPassword) {
            this.username = username;
            this.nickname = nickname;
            this.password = password;
            this.confirmPassword = confirmPassword;
        }

        public User toEntity(BCryptPasswordEncoder passwordEncoder) {
            return User.builder()
                    .username(this.getUsername())
                    .password(passwordEncoder.encode(this.getPassword()))
                    .nickname(this.getNickname())
                    .build();
        }
    }
}

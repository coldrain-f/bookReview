package edu.bookreview.controller;

import edu.bookreview.entity.User;
import edu.bookreview.service.UserService;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    // TODO: 2022-06-10 Validation 추가하면 됨
    @PostMapping("/api/signup")
    public void signup(@RequestBody @Valid AuthUserRequest authUserRequest) {
        User user = authUserRequest.toEntity(bCryptPasswordEncoder);
        userService.signup(user);
    }

    @Getter
    private static class AuthUserRequest {

        @NotBlank
        private final String username;

        @NotBlank
        private final String nickname;

        @NotBlank
        private final String password;

        @NotBlank
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

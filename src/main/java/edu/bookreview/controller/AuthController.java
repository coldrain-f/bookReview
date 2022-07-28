package edu.bookreview.controller;

import edu.bookreview.dto.UserLoginForm;
import edu.bookreview.entity.User;
import edu.bookreview.security.jwt.JWTUtil;
import edu.bookreview.service.UserService;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/signup")
    public void signup(@RequestBody @Valid AuthUserRequest authUserRequest) {
        String password = authUserRequest.getPassword();
        String confirmPassword = authUserRequest.getConfirmPassword();
        if (!password.equals(confirmPassword))
            throw new IllegalArgumentException("Passwords are not the same.");

        User user = authUserRequest.toEntity(bCryptPasswordEncoder);
        userService.signup(user);
    }

    // 이거 동작 안 되면 파업함.
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/authenticate")
    public JWTToken authorize(
            @RequestBody UserLoginForm loginForm, HttpServletResponse response) {
        String username = loginForm.getUsername();
        String password = loginForm.getPassword();

        User user = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Please check your ID or password"));

        if (!bCryptPasswordEncoder.matches(password, user.getPassword()))
            throw new IllegalArgumentException("Please check your ID or password");

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword(), null);

        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        String jwtToken = JWTUtil.makeAuthToken(user);
        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);

        return new JWTToken(jwtToken);
    }

    @Getter
    private static class JWTToken {
        private final String token;
        private final int expiresAt;

        public JWTToken(String token) {
            this.token = token;
            this.expiresAt = JWTUtil.AUTH_TIME_SECOND;
        }
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

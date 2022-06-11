package edu.bookreview.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import edu.bookreview.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JWTUtil {

    private static final Algorithm ALGORITHM = Algorithm.HMAC256("SECRET");
    public static final String JWT_COOKIE_NAME = "JWTToken";
    public static final int AUTH_TIME_SECOND = 60 * 20;
    public static final int AUTH_TIME_MILLIS = 60000 * 20; // 인증시간 20분
    public static final int REFRESH_TIME_MILLIS = 60000 * 60 * 24 * 7; // REFRESH 시간 7일

    public static String makeAuthToken(User user) {
        return JWT.create()
                .withSubject(user.getUsername()) // 토큰 제목
                .withExpiresAt(new Date(System.currentTimeMillis() + AUTH_TIME_MILLIS)) // 토큰 만료시간
                .sign(ALGORITHM); // 토큰 서명
    }

    public static String makeRefreshToken(User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TIME_MILLIS))
                .sign(ALGORITHM);
    }

    public static VerifyResult verify(String token) {
        // require() 로 Token 을 복호화 할 수 있다.
        // require() 로 복호화 했을 경우, 인증이 된 토큰이라면 예외가 발생하지 않고
        // 유효하지 않은 토큰이라면 Exception 이 발생한다.
        try {
            DecodedJWT verify = JWT.require(ALGORITHM).build().verify(token);
            return VerifyResult.builder().success(true)
                    .username(verify.getSubject()).build();

        } catch (Exception e) {
            // 인증이 되지 않은 토큰이라도 decode() 를 통해서 복호화 해볼 수 있다.
            DecodedJWT decode = JWT.decode(token);
            return VerifyResult.builder().success(false)
                    .username(decode.getSubject()).build();
        }
    }

}
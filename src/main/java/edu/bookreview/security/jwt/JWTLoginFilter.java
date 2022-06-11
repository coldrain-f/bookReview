package edu.bookreview.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bookreview.dto.UserLoginForm;
import edu.bookreview.security.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// JWT 로그인을 위한 필터
@Slf4j
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JWTLoginFilter(AuthenticationManager authenticationManager) {
        // 기본이 /login 이지만 명시적으로 적어줌.
        // POST /login 요청시 JWTLoginFilter 필터의 attemptAuthentication 가 동작함.
        super(authenticationManager);
        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 401 승인되지 않음
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        try {
            // UsernamePasswordAuthenticationFilter 가 했던 방식대로 사용자를 검증 해주면 된다.
            // 인증되기 전이므로 authorities 는 null 로 설정한다.
            UserLoginForm userLogin = objectMapper.readValue(request.getInputStream(), UserLoginForm.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    userLogin.getUsername(), userLogin.getPassword(), null
            );

            // AuthenticationManager 에게 AuthenticationToken 을 검증해 달라고 요청을 보낸다.
            // AuthenticationProvider 를 통해서 UserDetailsService 에서 User 를 가져와서 password 를 검증하고
            // 통과한다면 세션에 UserDetails 를 저장할 것이다. 성공했다면 successfulAuthentication() 으로 넘어간다.
            return this.getAuthenticationManager().authenticate(token);

        } catch (IOException e) {
            e.printStackTrace();
            throw new AuthenticationServiceException("This data is not in JSON form.");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // Header 에 토큰 발행 ( Bearer: Token 은 규약 )
        // authResult.getPrincipal() 을 통해서 세션에 저장된 PrincipalDetails 를 얻어올 수 있다.
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        String token = JWTUtil.makeAuthToken(principalDetails.getUser());
        Cookie cookie = new Cookie("JWTToken", token);
        cookie.setMaxAge(JWTUtil.AUTH_TIME_SECOND); // 쿠키의 만료 시간 20분
        cookie.setHttpOnly(true);
        cookie.setPath("/"); // 쿠키 사용경로
        response.addCookie(cookie);
        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }
}
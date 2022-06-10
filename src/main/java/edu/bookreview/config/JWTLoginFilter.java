package edu.bookreview.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bookreview.dto.UserLoginForm;
import edu.bookreview.entity.User;
import edu.bookreview.security.jwt.JWTUtil;
import org.aspectj.weaver.patterns.IToken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// JWT 로그인을 위한 필터
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JWTLoginFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        // 기본이 /login 이지만 명시적으로 적어줌.
        // POST /login 요청시 JWTLoginFilter 필터의 attemptAuthentication 가 동작함.
        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 여기서부터 공부 필요.
        try {
            // UsernamePasswordAuthenticationFilter 가 했던 방식대로 사용자를 검증 해주면 된다.
            // 인증되기 전이므로 authorities 는 null 로 설정한다.
            UserLoginForm userLogin = objectMapper.readValue(request.getInputStream(), UserLoginForm.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    userLogin.getUsername(), userLogin.getPassword(), null
            );
            // AuthenticationManager 에게 AuthenticationToken 을 검증해 달라고 요청을 보낸다.
            // AuthenticationProvider 를 통해서 UserDetailsService 에서 User 를 가져와서 password 를 검증하고
            // 세션에 UserDetails 를 저장할 것이다. 성공했다면 successfulAuthentication() 으로 넘어간다.
            return getAuthenticationManager().authenticate(token);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        //return super.attemptAuthentication(request, response);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // Header 에 토큰 발행 ( Bearer: Token 은 규약 )
        User user = (User) authResult.getPrincipal();
        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer: " + JWTUtil.makeAuthToken(user));
    }
}
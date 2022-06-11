package edu.bookreview.security.jwt;

import edu.bookreview.entity.User;
import edu.bookreview.security.PrincipalDetails;
import edu.bookreview.security.PrincipalService;
import edu.bookreview.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 로그인 된 JWT 토큰을 매 요청마다 체크해주기 위한 필터
@Slf4j
public class JWTCheckFilter extends BasicAuthenticationFilter {

    private final PrincipalService principalService;

    public JWTCheckFilter(AuthenticationManager authenticationManager, PrincipalService principalService) {
        super(authenticationManager);
        this.principalService = principalService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 토큰 검사
        String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearer == null || !bearer.startsWith("Bearer ")) {
            // 다음 Filter 를 타야 API 까지 가고, 돌아왔을 때 종료해준다.
            chain.doFilter(request, response);
            return;
        }

        String token = bearer.substring("Bearer ".length());
        VerifyResult verifyResult = JWTUtil.verify(token);

        if (verifyResult.isSuccess()) {
            log.info("인증이 완료된 사용자 = {}", verifyResult.getUsername());
            UserDetails principalDetails = principalService.loadUserByUsername(verifyResult.getUsername());

            UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
                    principalDetails, null, null
            );
            // 시큐리티 컨텍스트에 principalDetails 를 저장해놓고 API 에서 사용가능...
            SecurityContextHolder.getContext().setAuthentication(userToken);
            chain.doFilter(request, response);

        } else {
            throw new AuthenticationException("Token is not valid");
        }
    }
}
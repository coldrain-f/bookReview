package edu.bookreview.config;

import edu.bookreview.security.PrincipalService;
import edu.bookreview.security.jwt.JWTCheckFilter;
import edu.bookreview.security.jwt.JWTLoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalService principalService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // JWT 로그인을 위한 필터
        JWTLoginFilter loginFilter = new JWTLoginFilter(authenticationManager());

        // 로그인 된 JWT 토큰을 매 요청마다 체크해주기 위한 필터
        JWTCheckFilter checkFilter = new JWTCheckFilter(authenticationManager(), principalService);

        http
                .csrf().disable()
                // Token 을 사용하면 세션을 사용할 필요가 없음. ( OAuth2 사용시 세션 풀어줘야 함 )
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
                // 로그인 필터를 UsernamePasswordAuthenticationFilter 자리에 끼운다.
//                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterAt(checkFilter, BasicAuthenticationFilter.class)
                // OAuth2 로그인 설정 application.yml 설정과 Google Cloud Platform 에서 설정이 필요.
                // 승인된 리디렉션 URI http://localhost:8080/login/oauth2/code/google 을 추가해줘야 한다.
                .oauth2Login()
        ;

        http.authorizeRequests()
                .antMatchers("/api/signup").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
        ;

    }
}
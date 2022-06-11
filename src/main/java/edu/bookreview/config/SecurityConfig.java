package edu.bookreview.config;

import edu.bookreview.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

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
        JWTCheckFilter checkFilter = new JWTCheckFilter(authenticationManager(), userService);

        http
                .csrf().disable()
                .sessionManagement() // Token 을 사용하면 세션을 사용할 필요가 없음.
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 로그인 필터를 UsernamePasswordAuthenticationFilter 자리에 끼운다.
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(checkFilter, BasicAuthenticationFilter.class);

        http.authorizeRequests()
                // TODO: 2022-06-10 원할한 테스트를 위해서 /api/** 요청은 임시적으로 허용 (추후 지워야 됨)
                .antMatchers("/api/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().permitAll()
                .and()
                .formLogin();
        //.successForwardUrl("/");
    }
}

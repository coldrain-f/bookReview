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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalService principalService;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // TODO: 2022-06-13 배포 전 프론트 엔드 서버 URL 로 변경
        corsConfiguration.addAllowedOrigin(CorsConfiguration.ALL); // 허용할 URL
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL); // 허용할 Header
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL); // 허용할 Method
        corsConfiguration.setAllowCredentials(true); // 인증 정보 설정

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }


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
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .sessionManagement() // Token 을 사용하면 세션을 사용할 필요가 없음.
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 로그인 필터를 UsernamePasswordAuthenticationFilter 자리에 끼운다.
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(checkFilter, BasicAuthenticationFilter.class);

        http.authorizeRequests()
                // TODO: 2022-06-13
                .antMatchers("/images").permitAll()
                .antMatchers("/api/signup").permitAll()
                .antMatchers("/api/authentication").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated();
    }
}
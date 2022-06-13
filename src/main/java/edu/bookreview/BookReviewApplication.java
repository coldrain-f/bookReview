package edu.bookreview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableJpaAuditing
public class BookReviewApplication {

    static {
        System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
    }

    public static final String APPLICATION_LOCATIONS = "spring.config.location" + "classpath:application.yml";

    public static void main(String[] args) {
//        SpringApplication.run(BookReviewApplication.class, args);
        new SpringApplicationBuilder(BookReviewApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                // TODO: 2022-06-11 추후에 프론트엔드 서버 IP 주소로 변경해야 함.
//                registry.addMapping("/**").allowedOrigins("127.0.0.1");
//            }
//        };
//    }

}

package edu.bookreview;

import edu.bookreview.entity.SampleEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookReviewApplicationTests {

    @Test
    void contextLoads() {
        final SampleEntity member = SampleEntity.builder()
                .name("Olaf")
                .build();

        System.out.println("member = " + member);
    }

}

package edu.bookreview.controller;

import edu.bookreview.entity.SampleEntity;
import edu.bookreview.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SampleController {

    private final MemberRepository memberRepository;

    @GetMapping("/sample")
    public ResponseEntity<SampleEntity> sample() {
        SampleEntity member = memberRepository.findById(10L)
                .orElseThrow(() -> new IllegalArgumentException("없는 사용자의 ID 입니다."));

        return ResponseEntity.ok(member);
    }
}

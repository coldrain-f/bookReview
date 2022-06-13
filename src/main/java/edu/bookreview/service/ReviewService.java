package edu.bookreview.service;

import edu.bookreview.aws.AwsS3FileUploader;
import edu.bookreview.dto.ReviewCreateRequest;
import edu.bookreview.entity.Review;
import edu.bookreview.entity.User;
import edu.bookreview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final AwsS3FileUploader awsS3FileUploader;

    @Transactional
    public void writeReview(User user, ReviewCreateRequest reviewCreateRequest) {
        // S3 서버에 파일 업로드 후 저장된 파일의 URL 을 가지고 온다.
        String bookImageUrl = awsS3FileUploader.uploadFile(reviewCreateRequest.getFile());
        log.info("bookImageUrl = {}", bookImageUrl);

        // Review 생성 후 DB에 저장
        Review review = reviewCreateRequest.toEntity(user, bookImageUrl);
        reviewRepository.save(review);
    }
}

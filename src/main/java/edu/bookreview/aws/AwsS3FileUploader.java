package edu.bookreview.aws;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;


@Slf4j
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AwsS3FileUploader {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;

    public String uploadFile(MultipartFile multipartFile) {
        String fileName = createFileName(multipartFile);
        ObjectMetadata objectMetadata = new ObjectMetadata();

        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(
                    new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File upload failed.");
        }

        // Bucket 에서 파일 이름에 해당하는 URL 을 가지고 온다.
        // https://coldrain-f-bucket.s3.ap-northeast-2.amazonaws.com/images/1b7283f2-9c72-4c69-829b-11da5a9b0d4a585872.jpg
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private String createFileName(MultipartFile multipartFile) {
        return "images/" + UUID.randomUUID() + multipartFile.getOriginalFilename();
    }
}

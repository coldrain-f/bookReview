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
        log.info("contentLength = {}", multipartFile.getSize());
        log.info("contentType = {}", multipartFile.getContentType());

        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            log.info("bucket = {}", bucket);
            amazonS3Client.putObject(
                    new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File upload failed.");
        }

        return fileName;
    }

    private String createFileName(MultipartFile multipartFile) {
        return "static/" + UUID.randomUUID() + multipartFile.getOriginalFilename();
    }
}

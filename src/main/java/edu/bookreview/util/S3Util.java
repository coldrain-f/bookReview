package edu.bookreview.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Util {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;


    public String S3Uploader(MultipartFile file) {
        // TODO: 2022/06/13
        // aws s3 적용
        String imgFileName = createFileName(file);

        // TODO: 2022/06/13
        // aws s3 적용
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());
        try {
            amazonS3Client.putObject(
                    new PutObjectRequest(bucket, imgFileName, file.getInputStream(), objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));  // S3 업로드
//            log.info("content-type : {}, getInputStream : {}", imgFile.getFile().getContentType(), imgFile.getFile().getInputStream());

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File upload is failed.");
        }
        // 버킷에서 파일에 해당하는 url을 가져
        return amazonS3Client.getUrl(bucket, imgFileName).toString();
    }

    public void deleteS3File(String source){
        amazonS3Client.deleteObject(bucket, source);
    }

    private String createFileName(MultipartFile file) {
        // 파일 이름이 1.jpg 라면 originFilename 은 1.jpg이다.
        // 동일한 파일명이 업로드 된다면 덮어씌워지기 때문에 UUID_파일명으로 생성한다.
        //        // 파일명 생성이 어떻게 되는지 확인하기 위한 log
//        log.info("imageFileName = {}", imageFileName);
        return "images/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
    }
}

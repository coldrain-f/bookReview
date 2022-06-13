package edu.bookreview.util;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import edu.bookreview.dto.ImgFileDto;
import edu.bookreview.dto.ReviewsRequestDto;
import lombok.NoArgsConstructor;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@Slf4j
//@RequiredArgsConstructor
@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class SaveFile {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;


    @Value("${file.path}") // application.yml 에 설정된 외부경로, final 사용하면 안됨
    private String uploadFolder;

    public String saveFile(MultipartFile file) {
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


    // 이미지 파일 저장 경로는 외부로 분리해야한다.
    // 이유 1. java 파일은 .class 로 빌드 되는데 빠르게 끝나지만, 이미지 파일은 용량이 상대적으로 크기 때문에 target 폴더로 빌드되는데 오래걸리기 때문(용량문제)
    // 이유 2. 이미지 파일을 업로드하고 빌듣되는 시간이 오래걸리면 사진을 화면에서 렌더링 했을 때 렌더링 되는 시간보다 빌드되는 시간이 오래 걸려서 액스박스가 표시 될 수 있다.(액박 문제)
    // local에 파일 저장
    private String localFileSave(MultipartFile file){
        // TODO: 2022/06/11
        String imgFileName = createFileName(file);
        // 실제 이미지 파일이 어디에 저장될지 경로를 지정해줘야 한다.
        // 경로는 application.yml에 저장되어있다.
        Path imgFilePath = Paths.get(uploadFolder + imgFileName);

        // I/O 사용시에는 예외가 발생할 수 있으므로 try catch로 감싸줘야한다.
        try {
            // 1. 저장할 파일 경로 2. 저장할 파일
            Files.write(imgFilePath, file.getBytes());
        } catch (IOException e){
            e.printStackTrace();
        }
        return imgFilePath.toString();
    }



    private String createFileName(MultipartFile file) {
        // 파일 이름이 1.jpg 라면 originFilename 은 1.jpg이다.
        // 동일한 파일명이 업로드 된다면 덮어씌워지기 때문에 UUID_파일명으로 생성한다.
        //        // 파일명 생성이 어떻게 되는지 확인하기 위한 log
//        log.info("imageFileName = {}", imageFileName);
        return "images/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
    }
}
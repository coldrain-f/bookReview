package edu.bookreview.controller;

import edu.bookreview.aws.AwsS3FileUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class S3FileUploadSampleController {

    private final AwsS3FileUploader awsS3FileUploader;

    @PostMapping("/images")
    public String upload(@RequestParam("images") MultipartFile multipartFile) {
        return awsS3FileUploader.uploadFile(multipartFile);
    }
}
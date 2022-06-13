package edu.bookreview.controller;

import edu.bookreview.aws.AwsS3FileUploader;
import edu.bookreview.aws.AwsS3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class S3Controller {

    private final AwsS3FileUploader fileUploader;

    @PostMapping("/images")
    public String upload(@RequestParam("images") MultipartFile multipartFile) throws IOException {
        return fileUploader.uploadFile(multipartFile);
    }
}

package edu.bookreview.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class ImgFileDto {
    private MultipartFile file;
}

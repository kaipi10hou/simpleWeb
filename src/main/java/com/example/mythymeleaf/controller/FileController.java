package com.example.mythymeleaf.controller;

import com.example.mythymeleaf.dto.FileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/fl")
public class FileController {

    @Value("${file.upload.url}")
    String url;

    @GetMapping("/list")
    public String list() {
        return "fileboard/file-board";
    }

    @PostMapping("/upload")
    // 업로드하는 파일들을 MultipartFile 형태의 파라미터로 전달된다.
    public String upload(@RequestParam("uploadfile") MultipartFile uploadfile)  throws IllegalStateException, IOException {
        List<FileDto> list = new ArrayList<>();
        if (!uploadfile.isEmpty()) {
            // UUID를 이용해 unique한 파일 이름을 만들어준다.
            FileDto dto = new FileDto(UUID.randomUUID().toString(),
                    uploadfile.getOriginalFilename(),
                    uploadfile.getContentType());
            list.add(dto);

            String pathName= url + dto.getUuid() + "_" + dto.getFileName();
            //redis에 pathName을 저장하여 보존하는 걸로
            File newFileName = new File(pathName);
            // 전달된 내용을 실제 물리적인 파일로 저장해준다.
            uploadfile.transferTo(newFileName);
        }
        return "fileboard/file-board";
    }

}

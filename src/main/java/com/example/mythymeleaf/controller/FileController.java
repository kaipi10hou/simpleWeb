package com.example.mythymeleaf.controller;

import com.example.mythymeleaf.dto.FileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/fl")
public class FileController {

    @GetMapping("/list")
    public String list() {
        return "fileboard/file-board";
    }

    @PostMapping("/upload")
    // 업로드하는 파일들을 MultipartFile 형태의 파라미터로 전달된다.
    public String upload(@RequestParam MultipartFile[] uploadfile, Model model) throws IllegalStateException, IOException {
        List<FileDto> list = new ArrayList<>();
        for (MultipartFile file : uploadfile) {
            if (!file.isEmpty()) {
                // UUID를 이용해 unique한 파일 이름을 만들어준다.
                FileDto dto = new FileDto(UUID.randomUUID().toString(),
                        file.getOriginalFilename(),
                        file.getContentType());
                list.add(dto);

                File newFileName = new File(dto.getUuid() + "_" + dto.getFileName());
                // 전달된 내용을 실제 물리적인 파일로 저장해준다.
                file.transferTo(newFileName);
            }
        }
        model.addAttribute("files", list);
        return "result";
    }

//    @GetMapping("/form")
//    public String form(Model model, @RequestParam(required = false) Long id){
//        if(id == null){
//            model.addAttribute("fl", new Board());
//        }else{
//            Board fl = flService.findById(id).orElseGet(null);
//            model.addAttribute("fl", fl);
//        }
//        return "fl/form";
//    }

//    @PostMapping("/form")
//    public String flSubmit(@Valid Board fl, BindingResult bindingResult, Authentication authentication){
//        flValidator.validate(fl, bindingResult);
//        if(bindingResult.hasErrors())
//            return "fl/form";
//        String username = authentication.getName();
//        fl.setUser(username);
//        flService.save(username, fl);
//        return "redirect:/fl/list";
//    }
}

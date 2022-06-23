package com.example.mythymeleaf.controller;

import com.example.mythymeleaf.dto.FileDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/fl")
public class FileController {

    @Value("${spring.servlet.multipart.location}")
    String url;

    String fileExt;

//    @Value("${file.available.extension}")
//    String availableExts;

    @GetMapping("/list")
    public String list() {
        return "fileboard/file-board";
    }

    @PostMapping("/upload")
    // 업로드하는 파일들을 MultipartFile 형태의 파라미터로 전달된다.
    public String upload(@RequestParam("uploadfile") MultipartFile uploadfile)  throws IllegalStateException, IOException {
        if (!uploadfile.isEmpty()) {
//            FileDto dto = new FileDto(UUID.randomUUID().toString(),
//                    uploadfile.getOriginalFilename(),
//                    uploadfile.getContentType());
//            list.add(dto);
//            String pathName= url + dto.getUuid() + "_" + dto.getFileName();
            String fileName = uploadfile.getOriginalFilename();
            fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
            File newFileName = new File(url + "my-file" + "." + fileExt);
            uploadfile.transferTo(newFileName);
        }
        return "fileboard/file-board";
    }

    @GetMapping("/download")
    public void download(HttpServletResponse response, HttpServletRequest request) throws Exception {
//        try {
            String path = url + "my-file." + fileExt ;// 경로에 접근할 때 역슬래시('\') 사용
            String fileName = "my-file."+fileExt;
//
            File file = new File(path);
//            response.setHeader("Content-Disposition", "attachment;filename=" + file.getName()); // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를 알려주는 헤더
//
//            FileInputStream fileInputStream = new FileInputStream(path); // 파일 읽어오기
//            OutputStream out = response.getOutputStream();
//
//            int read = 0;
//            byte[] buffer = new byte[1024];
//            while ((read = fileInputStream.read(buffer)) != -1) { // 1024바이트씩 계속 읽으면서 outputStream에 저장, -1이 나오면 더이상 읽을 파일이 없음
//                out.write(buffer, 0, read);
//            }
//
//        } catch (Exception e) {
//            throw new Exception("download error");
//        }

        FileInputStream fileInputStream = null;
        ServletOutputStream servletOutputStream = null;

        try{
            String downName = null;
            String browser = request.getHeader("User-Agent");
            //파일 인코딩
            if(browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")){//브라우저 확인 파일명 encode

                downName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");

            }else{

                downName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");

            }

            response.setHeader("Content-Disposition","attachment;filename=\"" + downName+"\"");
            response.setContentType("application/octer-stream");
            response.setHeader("Content-Transfer-Encoding", "binary;");

            fileInputStream = new FileInputStream(file);
            servletOutputStream = response.getOutputStream();

            byte b [] = new byte[1024];
            int data = 0;

            while((data=(fileInputStream.read(b, 0, b.length))) != -1){

                servletOutputStream.write(b, 0, data);

            }

            servletOutputStream.flush();//출력

        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(servletOutputStream!=null){
                try{
                    servletOutputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(fileInputStream!=null){
                try{
                    fileInputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

}

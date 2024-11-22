package com.ecyce.karma.domain.s3;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/s3")
@RequiredArgsConstructor
public class S3TestController {

    private final S3Uploader s3Uploader;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        log.info("단일 파일 업로드 요청 수신: {}", file.getOriginalFilename());
        String uploadedUrl = s3Uploader.saveFile(file);
        log.info("업로드 완료된 파일 URL: {}", uploadedUrl);
        return ResponseEntity.ok("업로드된 파일 URL: " + uploadedUrl);
    }

    @PostMapping("/upload/multiple")
    public ResponseEntity<List<String>> uploadMultipleFiles(@RequestParam("files") List<MultipartFile> files) {
        log.info("다중 파일 업로드 요청 수신. 파일 수: {}", files.size());
        List<String> uploadedUrls = s3Uploader.saveFiles(files);
        log.info("다중 파일 업로드 완료. 업로드된 파일 URL 목록: {}", uploadedUrls);
        return ResponseEntity.ok(uploadedUrls);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam("fileUrl") String fileUrl) {
        log.info("파일 삭제 요청 수신: {}", fileUrl);
        s3Uploader.deleteFile(fileUrl);
        log.info("파일 삭제 완료: {}", fileUrl);
        return ResponseEntity.ok("파일 삭제 성공: " + fileUrl);
    }
}
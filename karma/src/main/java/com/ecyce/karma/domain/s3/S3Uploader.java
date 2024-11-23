package com.ecyce.karma.domain.s3;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ecyce.karma.global.exception.CustomException;
import com.ecyce.karma.global.exception.ErrorCode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3Uploader {

    private final AmazonS3 amazonS3;

    private Set<String> uploadedFileNames = new HashSet<>();
    private Set<Long> uploadedFileSizes = new HashSet<>();

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxSizeString;

    // 여러 장의 파일 저장
    public List<String> saveFiles(List<MultipartFile> multipartFiles) {
        log.info("다중 파일 업로드 요청 수신. 파일 수: {}", multipartFiles.size());
        List<String> uploadedUrls = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            log.debug("현재 파일 처리 중: 파일명 = {}, 크기 = {}", multipartFile.getOriginalFilename(), multipartFile.getSize());
            if (isDuplicate(multipartFile)) {
                log.warn("중복된 파일 감지: 파일명 = {}, 크기 = {}", multipartFile.getOriginalFilename(), multipartFile.getSize());
                throw new CustomException(ErrorCode.DUPLICATE_FILE);
            }

            String uploadedUrl = saveFile(multipartFile);
            uploadedUrls.add(uploadedUrl);
        }

        clear();
        log.info("모든 파일 업로드 완료. 업로드된 파일 URL 목록: {}", uploadedUrls);
        return uploadedUrls;
    }

    // 파일 삭제
    public void deleteFile(String fileUrl) {
        log.info("파일 삭제 요청 수신: {}", fileUrl);
        String[] urlParts = fileUrl.split("/");
        String fileBucket = urlParts[2].split("\\.")[0];
        log.debug("분석된 S3 버킷: {}", fileBucket);

        if (!fileBucket.equals(bucket)) {
            log.error("S3 버킷 이름 불일치: 요청 버킷 = {}, 실제 버킷 = {}", fileBucket, bucket);
            throw new CustomException(ErrorCode.S3_BUCKET_MISMATCH);
        }

        String objectKey = String.join("/", Arrays.copyOfRange(urlParts, 3, urlParts.length));
        log.debug("분석된 Object Key: {}", objectKey);

        if (!amazonS3.doesObjectExist(bucket, objectKey)) {
            log.error("S3에서 파일을 찾을 수 없음: {}", objectKey);
            throw new CustomException(ErrorCode.S3_FILE_NOT_FOUND);
        }

        try {
            amazonS3.deleteObject(bucket, objectKey);
            log.info("파일 삭제 성공: {}", objectKey);
        } catch (AmazonS3Exception e) {
            log.error("S3 파일 삭제 실패: {}", e.getMessage());
            throw new CustomException(ErrorCode.S3_DELETE_ERROR);
        } catch (SdkClientException e) {
            log.error("AWS SDK 클라이언트 오류로 인해 파일 삭제 실패: {}", e.getMessage());
            throw new CustomException(ErrorCode.S3_DELETE_ERROR);
        }
    }

    // 단일 파일 저장
    public String saveFile(MultipartFile file) {
        log.info("단일 파일 업로드 요청 수신: 파일명 = {}, 크기 = {}", file.getOriginalFilename(), file.getSize());
        String randomFilename = generateRandomFilename(file);
        log.debug("생성된 랜덤 파일명: {}", randomFilename);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try {
            amazonS3.putObject(bucket, randomFilename, file.getInputStream(), metadata);
            log.info("파일 업로드 성공: {}", randomFilename);
        } catch (AmazonS3Exception e) {
            log.error("S3 파일 업로드 중 Amazon S3 오류 발생: {}", e.getMessage());
            throw new CustomException(ErrorCode.S3_UPLOAD_ERROR);
        } catch (SdkClientException e) {
            log.error("AWS SDK 클라이언트 오류로 인해 파일 업로드 실패: {}", e.getMessage());
            throw new CustomException(ErrorCode.S3_UPLOAD_ERROR);
        } catch (IOException e) {
            log.error("파일 업로드 중 IO 오류 발생: {}", e.getMessage());
            throw new CustomException(ErrorCode.S3_UPLOAD_ERROR);
        }

        return amazonS3.getUrl(bucket, randomFilename).toString();
    }

    // 요청에 중복되는 파일 여부 확인
    private boolean isDuplicate(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        Long fileSize = multipartFile.getSize();
        log.debug("중복 확인: 파일명 = {}, 크기 = {}", fileName, fileSize);

        if (uploadedFileNames.contains(fileName) && uploadedFileSizes.contains(fileSize)) {
            return true;
        }

        uploadedFileNames.add(fileName);
        uploadedFileSizes.add(fileSize);
        return false;
    }

    private void clear() {
        uploadedFileNames.clear();
        uploadedFileSizes.clear();
        log.debug("중복 파일 검증 데이터 초기화 완료.");
    }

    private String generateRandomFilename(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String fileExtension = validateFileExtension(originalFilename);
        String randomFilename = UUID.randomUUID() + "." + fileExtension;
        log.debug("랜덤 파일명 생성: 원본 파일명 = {}, 생성된 파일명 = {}", originalFilename, randomFilename);
        return randomFilename;
    }

    private String validateFileExtension(String originalFilename) {
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        List<String> allowedExtensions = Arrays.asList("jpg", "png", "gif", "jpeg");

        if (!allowedExtensions.contains(fileExtension)) {
            log.error("지원되지 않는 파일 확장자: {}", fileExtension);
            throw new CustomException(ErrorCode.INVALID_FILE_EXTENSION);
        }

        log.debug("유효한 파일 확장자 확인: {}", fileExtension);
        return fileExtension;
    }
}


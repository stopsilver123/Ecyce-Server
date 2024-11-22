package com.ecyce.karma.global.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Slf4j
@Configuration
public class S3Config {

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;
    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    public AmazonS3 amazonS3() {
        log.info("AmazonS3 빈 생성 시작");
        log.info("AWS Access Key: {}", accessKey);
        log.info("AWS Region: {}", region);

        try {
            AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
            AmazonS3 amazonS3 = AmazonS3ClientBuilder
                    .standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withRegion(region)
                    .build();

            log.info("AmazonS3 빈 생성 완료");
            return amazonS3;
        } catch (Exception e) {
            log.error("AmazonS3 빈 생성 중 오류 발생: {}", e.getMessage());
            throw e;
        }
    }
}
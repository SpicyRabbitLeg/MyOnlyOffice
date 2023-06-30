package com.onlyoffice.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Configuration(proxyBeanMethods = false)
public class MinioConfig {
    private final MinioProperties minioProperties;

    public MinioConfig(MinioProperties minioProperties){
        this.minioProperties = minioProperties;
    }

    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder()
                .endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getAccessKey(),minioProperties.getSecretKey()).build();
    }

    @ConfigurationProperties(prefix = "minio")
    @Component
    @Data
    public static class MinioProperties {
        @NotNull
        private String endpoint;
        @NotNull
        private String accessKey;
        @NotNull
        private String secretKey;
        private String bucket = "temp";
    }
}

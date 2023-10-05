package com.example.infosystems.config;

import io.minio.MinioClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

    @Value("${minio.endpoint}")

    private String endpoint;

    @Value("${minio.accesskey}")
    private String accesskey;

    @Value("${minio.secretkey}")
    private String secretkey;


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public MinioClient minioClient(){
       return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accesskey,secretkey)
                .build();
    }
}

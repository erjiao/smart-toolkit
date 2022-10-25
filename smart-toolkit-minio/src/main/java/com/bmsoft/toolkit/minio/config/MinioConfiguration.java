package com.bmsoft.toolkit.minio.config;


import com.bmsoft.toolkit.minio.controller.STFileController;
import com.bmsoft.toolkit.minio.util.MinioUtil;
import io.minio.MinioClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author llk
 * @date 2022-10-18 19:30
 */
@Configuration
@ConditionalOnProperty(value = "smart-toolkit.minio.enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(MinioProperties.class)
public class MinioConfiguration {


    @Bean
    public MinioClient minioClient(MinioProperties properties) {
        return MinioClient.builder()
                .endpoint(properties.getEndpoint())
                .credentials(properties.getAccessKey(), properties.getSecretKey())
                .build();
    }


    @Bean
    public MinioUtil minioUtil(MinioClient minioClient, MinioProperties minioProperties) {
        return new MinioUtil(minioClient, minioProperties);
    }

    @Bean
    @ConditionalOnProperty(value = "smart-toolkit.minio.file-controller.enabled", havingValue = "true", matchIfMissing = true)
    public STFileController stFileController() {
        return new STFileController();
    }

}

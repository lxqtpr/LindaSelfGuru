package dev.lxqtpr.lindaSelfGuru.Core.Configs;

import dev.lxqtpr.lindaSelfGuru.Core.Properties.MinioProperties;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MinioConfig{

    private final MinioProperties minioProperties;
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioProperties.getUrl())
                .credentials(
                        minioProperties.getAccessKey(),
                        minioProperties.getSecretKey()
                )
                .build();
    }
}
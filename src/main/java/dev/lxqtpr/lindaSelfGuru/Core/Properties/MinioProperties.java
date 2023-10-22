package dev.lxqtpr.lindaSelfGuru.Core.Properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "minio")
@Component
public class MinioProperties {
    private String url;
    private String accessKey;
    private String secretKey;
}
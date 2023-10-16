package dev.lxqtpr.lindaSelfGuru.Core.Properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "security.jwt")
@Component
public class JwtProperties {
    private String accessSecret;
    private String refreshSecret;
    private Long accessExpirationInMinutes;
    private Long refreshExpirationInMinutes;
}

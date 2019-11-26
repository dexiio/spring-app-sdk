package io.dexi.service.config;

import io.dexi.oauth.OAuthEncryptionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@ConditionalOnBean(DexiEncryptionProperties.class)
public class DexiEncryptionServiceConfig {

    private final DexiEncryptionProperties properties;

    public DexiEncryptionServiceConfig(DexiEncryptionProperties properties) {
        this.properties = properties;
    }

    @Bean
    public OAuthEncryptionService oAuthEncryptionService() {

        if (StringUtils.isBlank(properties.getKey())) {
            throw new IllegalArgumentException("Missing required configuration property: encryption.key");
        }

        return new OAuthEncryptionService(properties.getKey());
    }
}

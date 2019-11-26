package io.dexi.service.config;

import io.dexi.client.DexiAuth;
import io.dexi.client.DexiClientFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn("dexiConfigWrapper")
public class DexiClientConfig {

    private final DexiConfigProperties properties;

    @Autowired
    public DexiClientConfig(DexiConfigProperties properties) {
        this.properties = properties;
    }

    @Bean
    public DexiClientFactory dexiClient() {

        if (StringUtils.isBlank(properties.getBaseUrl())) {
            throw new IllegalArgumentException("Missing required configuration property: dexi.base-url");
        }

        if (StringUtils.isBlank(properties.getAccount())) {
            throw new IllegalArgumentException("Missing required configuration property: dexi.account");
        }

        if (StringUtils.isBlank(properties.getApiKey())) {
            throw new IllegalArgumentException("Missing required configuration property: dexi.api-key");
        }

        return new DexiClientFactory(properties.getBaseUrl(), DexiAuth.from(properties.getAccount(), properties.getApiKey()));
    }

}

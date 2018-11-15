package io.dexi.service.config;

import io.dexi.client.DexiAuth;
import io.dexi.client.DexiClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static io.dexi.config.DexiConfig.DEFAULT_BASE_URL;

@Configuration
public class DexiSpringConfig {

    @Value("${dexi.base_url:" + DEFAULT_BASE_URL + "}")
    private String dexiBaseUrl;

    @Value("${dexi.apikey}")
    private String dexiApiKey;

    @Value("${dexi.account}")
    private String dexiAccountId;

    @Bean
    public DexiClientFactory dexiClient() {
        return new DexiClientFactory(dexiBaseUrl, DexiAuth.from(dexiAccountId, dexiApiKey));
    }
}

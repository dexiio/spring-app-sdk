package io.dexi.service.config;

import io.dexi.config.DexiConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.validation.constraints.NotBlank;

@Configuration
@DependsOn("dexiConfigWrapper")
@ConfigurationProperties(prefix = "dexi")
public class DexiConfigProperties {

    @NotBlank
    private String baseUrl = DexiConfig.DEFAULT_BASE_URL;

    @NotBlank
    private String account;

    @NotBlank
    private String apiKey;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}

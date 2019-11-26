package io.dexi.service.config;

import io.dexi.config.DexiConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.validation.constraints.NotBlank;

@Configuration
@ConditionalOnProperty( prefix = "encryption", name = "enabled", havingValue = "true")
@DependsOn("dexiConfigWrapper")
@ConfigurationProperties(prefix = "encryption")
public class DexiEncryptionProperties {

    @NotBlank
    private String key;

    private boolean enabled = false;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

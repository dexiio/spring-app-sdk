package io.dexi.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;

@Configuration
public class DexiSpringConfig {

    @Autowired
    private ConfigurableEnvironment environment;

    @Bean("dexiConfigWrapper")
    public DexiConfigWrapper dexiConfigWrapper() {
        DexiConfigWrapper dexiConfigWrapper = new DexiConfigWrapper();
        MutablePropertySources propertySources = environment.getPropertySources();
        propertySources.addFirst(dexiConfigWrapper);
        return dexiConfigWrapper;
    }

}

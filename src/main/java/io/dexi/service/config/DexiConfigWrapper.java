package io.dexi.service.config;

import io.dexi.config.DexiConfig;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.core.env.PropertySource;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;


public class DexiConfigWrapper extends PropertySource<URI> {

    public DexiConfigWrapper() {
        super("dexi");

        try {
            DexiConfig.load();

        } catch (ConfigurationException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getProperty(String key) {
        return DexiConfig.getProperties().get(key);
    }

}

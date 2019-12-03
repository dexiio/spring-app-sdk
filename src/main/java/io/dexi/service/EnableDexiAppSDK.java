package io.dexi.service;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Put this next to your SpringBootApplication annotation on your main class to enable the Dexi App SDK
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(DexiAppSDKConfig.class)
public @interface EnableDexiAppSDK {
}

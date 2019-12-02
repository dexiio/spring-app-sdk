package io.dexi.service.handlers;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Put this annotation on all your component handlers. This will enable Spring to automatically detect and wire them up
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface AppComponent {

    /**
     * The name of the component as defined in your dexi.yml file. Must match exactly.
     * @return
     */
    @AliasFor(annotation = Component.class)
    String value();
}

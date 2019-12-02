package io.dexi.service.components;

import io.dexi.service.exceptions.ComponentConfigurationException;
import io.dexi.service.AppContext;

/**
 * Implement this to validate component configuration for a given activation.
 *
 * @param <T> the activation configuration DTO
 * @param <U> the component configuration DTO
 */
public interface ComponentValidatesConfiguration<T, U> extends BaseAppComponent<U> {
    /**
     * Is invoked whenever the user uses a component and clicks "Test configuration" for a component.
     *
     * Will be exposed as POST /dexi/component/support/validate
     *
     * @param ctxt Context information about the current activation and component configuration
     * @throws ComponentConfigurationException throws exception if configuration is invalid
     */
    void validate(AppContext<T,U> ctxt) throws ComponentConfigurationException;

}

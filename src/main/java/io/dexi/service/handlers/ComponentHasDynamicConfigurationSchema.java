package io.dexi.service.handlers;

import io.dexi.service.Schema;

import java.net.URISyntaxException;

/**
 * Implement this to handle dynamic configuration for components
 *
 * @param <T> the activation configuration DTO
 * @param <U> the component configuration DTO
 */
public interface ComponentHasDynamicConfigurationSchema<T, U> extends BaseComponentHandler<U> {
    /**
     * Method that is invoked whenver configuration is refreshed. Is used to return a new field schema
     * to dexi for supporting dynamic configuration forms.
     *
     * Will be exposed as POST /dexi/data/dynamic-configuration/read
     *
     * @param ctxt Context information about the current activation and component configuration
     * @return the schema generated based on the inputs
     */
    Schema getConfigurationSchema(AppContext<T,U> ctxt);

}

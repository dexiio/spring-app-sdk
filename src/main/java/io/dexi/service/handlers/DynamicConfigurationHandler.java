package io.dexi.service.handlers;

import io.dexi.service.Schema;

import java.net.URISyntaxException;

/**
 * Implement this handler to handle dynamic configuration for components
 *
 * @param <T> the activation configuration DTO
 * @param <U> the component configuration DTO
 */
public interface DynamicConfigurationHandler<T, U> {
    /**
     * Method that is invoked whenver configuration is refreshed. Is used to return a new field schema
     * to dexi for supporting dynamic configuration forms.
     *
     * @param ctxt Context information about the current activation and component configuration
     * @return the schema generated based on the inputs
     */
    Schema getConfiguration(AppContext<T,U> ctxt);

}

package io.dexi.service.handlers;

import io.dexi.service.Schema;

/**
 * Implement this handler to handle dynamic schemas (inputs / outputs) for components.
 *
 * @param <T> the activation configuration DTO
 * @param <U> the dynamic schema payload DTO
 */
public interface DynamicSchemaHandler<T, U> {
    /**
     * Method that gets invoked whenever the schema for a component (input / output) is needed. Returns
     * a JSON schema-like structure which is used within dexi. Implementing this will allow for dynamic
     * schemas based on configuration.
     *
     * @param activationId the ID of the users app activation
     * @param activationConfig the activation configuration DTO
     * @param componentName the name of the component being used as defined in your dexi.yml
     * @param componentConfig the component configuration DTO
     * @return the schema based on the configuration provided.
     */
    Schema getSchema(String activationId, T activationConfig, String componentName, U componentConfig);

    /**
     * Get the component configuration class. Is used for (de)serialization
     *
     * @param componentName the name of the component as defined in your dexi.yml
     *
     * @return the class itself (U)
     */
    Class<? extends U> getComponentConfigClass(String componentName);
}

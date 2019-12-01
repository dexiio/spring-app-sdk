package io.dexi.service.handlers;

import io.dexi.service.DynamicSchemaConfig;
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
     * @param ctxt Context information about the current activation and component configuration
     * @return the schema based on the configuration provided.
     */
    Schema getSchema(AppContext<T, DynamicSchemaConfig<U>> ctxt);

}

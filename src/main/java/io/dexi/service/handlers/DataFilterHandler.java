package io.dexi.service.handlers;

import io.dexi.service.utils.ResultStream;
import io.dexi.service.utils.RowStream;

/**
 * Implement this handler to handle data-filter components.
 *
 * @param <T> the activation configuration DTO
 * @param <U> the component configuration DTO
 */
public interface DataFilterHandler<T, U> {

    /**
     * Method that is invoked for all data-filter components for belonging to this app.
     *
     * @param activationId the ID of the users app activation
     * @param activationConfig the activation configuration DTO
     * @param componentName the name of the component being used as defined in your dexi.yml
     * @param componentConfig the component configuration DTO
     * @param inputs a stream of inputs coming from dexi
     * @param outputs a stream of outputs from your app.
     */
    void filter(String activationId, T activationConfig, String componentName, U componentConfig, RowStream inputs, ResultStream outputs);

    /**
     * Get the component configuration class. Is used for (de)serialization
     *
     * @param componentName the name of the component as defined in your dexi.yml
     *
     * @return the class itself (U)
     */
    Class<U> getComponentConfigClass(String componentName);
}

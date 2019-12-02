package io.dexi.service.handlers;

/**
 * Base interface for all component type interfaces
 * @param <U>
 */
public interface BaseComponentHandler<U> {

    /**
     * Returns class for component configuration DTO. Is used for (de)serialization
     *
     * @return the class itself (U)
     */
    Class<U> getComponentConfigClass();
}

package io.dexi.service.components;

/**
 * Base interface for all component type interfaces
 * @param <U>
 */
public interface BaseAppComponent<U> {

    /**
     * Returns class for component configuration DTO. Is used for (de)serialization
     *
     * @return the class itself (U)
     */
    Class<U> getComponentConfigClass();
}

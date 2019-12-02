package io.dexi.service.handlers;

import io.dexi.service.exceptions.ActivationException;

/**
 * The activation handler provides methods for validating activation configuration as well as reacting to activations
 * and deactivations if needed.
 *
 * NOTE: Implementing this interface is required
 *
 * @param <T> Specify a DTO for the configuration options. Can be an empty class if no configuration properties are needed.
 */
public interface ActivationHandler<T> {
    /**
     * Returns the configuration options class - used for (de)serialization
     *
     * @return the DTO for the configuration options
     */
    Class<T> getActivationConfigClass();

    /**
     * Method that is invoked whenever a user validates a configuration. Happens before activation and whenever the user
     * clicks "Test configuration".
     *
     * Will be exposed as POST /dexi/activations/validate
     *
     * Note: Method implementation is required
     *
     * @param activationConfig the users configuration options
     * @throws ActivationException throws activation exceptions if validation fails. Can also throw RuntimeExceptions
     */
    void validate(T activationConfig) throws ActivationException;

    /**
     * Is invoked whenever a user activates the app. Is already validated ahead of the call through the validate method.
     *
     * Will be exposed as POST /dexi/activations/activate
     *
     * Note: Method is optional and can be left in it's default "do-nothing" state
     *
     * @param activationId the users activation id - can be used as a reference in the app if needed
     * @param activationConfig the configuration DTO with the users values
     * @throws ActivationException throws if the activation fails and should be prevented.
     */
    default void activate(String activationId, T activationConfig) throws ActivationException {}

    /**
     * Is invoked whenever a user deactivates the app.
     *
     * Will be exposed as POST /dexi/activations/deactivate
     *
     * Note: Method is optional and can be left in it's default "do-nothing" state
     *
     * @param activationId the activation id of the activation that is being deactivated.
     * @throws ActivationException throws if the deactivation fails and should be prevented.
     */
    default void deactivate(String activationId) throws ActivationException {}
}

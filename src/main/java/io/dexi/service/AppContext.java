package io.dexi.service;

public class AppContext<T,U> {

    /**
     * the ID of the users app activation
     */
    private final String activationId;

    /**
     * the activation configuration DTO
     */
    private final T activationConfig;

    /**
     * the name of the component being used as defined in your dexi.yml
     */
    private final String componentName;

    /**
     * the component configuration DTO
     */
    private final U componentConfig;

    public AppContext(String activationId, T activationConfig, String componentName, U componentConfig) {
        this.activationId = activationId;
        this.activationConfig = activationConfig;
        this.componentName = componentName;
        this.componentConfig = componentConfig;
    }

    public String getActivationId() {
        return activationId;
    }

    public T getActivationConfig() {
        return activationConfig;
    }

    public String getComponentName() {
        return componentName;
    }

    public U getComponentConfig() {
        return componentConfig;
    }
}

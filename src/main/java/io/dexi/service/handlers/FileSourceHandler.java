package io.dexi.service.handlers;

import javax.servlet.http.HttpServletResponse;

public interface FileSourceHandler<T, U> {
    void read(T activationConfig, U componentConfig, HttpServletResponse response);

    Class<U> getComponentConfigClass(String componentName);
}

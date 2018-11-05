package io.dexi.service.config;

import io.dexi.service.exceptions.UserErrorException;

import java.util.regex.Pattern;

abstract public class FileSystemComponentConfig implements ComponentConfig {

    private static Pattern unixPathValidation = Pattern.compile("^(/([^/\0][^/\0]*)?)*/?$");

    private String path;

    @Override
    public void validate() {
        if (path == null || path.isEmpty()) {
            throw new UserErrorException("Path was empty");
        }

        if (!path.startsWith("/")) {
            throw new UserErrorException("Path was not absolute");
        }

        if (!unixPathValidation.matcher(path).matches()) {
            throw new UserErrorException("Path contained invalid characters or formatting");
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

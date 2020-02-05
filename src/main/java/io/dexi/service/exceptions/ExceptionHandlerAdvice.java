package io.dexi.service.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlerAdvice {
    private static final Logger log = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    @ExceptionHandler
    public void handle(Exception e, HttpServletRequest request) throws Exception {
        log.warn("Call to REST method failed: {} {}", request.getMethod(), request.getServletPath(), e) ;
        throw e;
    }
}
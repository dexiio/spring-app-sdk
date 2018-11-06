package io.dexi.service.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HealthController {

    /**
     * Health endpoint. Should check connection to external services etc.
     * Used to determine if service is healthy and ready to serve requests.
     */
    @RequestMapping(value = "/_health", method = RequestMethod.GET)
    public boolean health() {
        return true;
    }

    /**
     * Ping endpoint. Should not do anything but respond.
     * Used to determine if REST layer is available.
     */
    @RequestMapping(value = "/_ping", method = RequestMethod.GET)
    public String ping() {
        return "pong";
    }

}

package io.dexi.service.rest;

import io.dexi.service.exceptions.ActivationException;
import io.dexi.service.handlers.AppHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@ConditionalOnBean(AppHandler.class)
@RestController
@RequestMapping("/apps/")
public class AppController {

    @Autowired
    private AppHandler appHandler;

    @RequestMapping(value = "activate", method = RequestMethod.POST)
    public void activate(@RequestBody Object app) throws ActivationException {
        appHandler.activate(app);
    }

    @RequestMapping(value = "deactivate", method = RequestMethod.POST)
    public void deactivate(@RequestBody Object app) throws ActivationException {
        appHandler.deactivate(app);
    }

}

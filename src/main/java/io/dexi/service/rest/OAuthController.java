package io.dexi.service.rest;

import io.dexi.oauth.EncryptedOAuthTokens;
import io.dexi.oauth.OAuthEncryptionService;
import io.dexi.oauth.OAuthTokens;
import io.dexi.oauth.payloads.OAuthRedirectRequest;
import io.dexi.oauth.payloads.OAuthRedirectResponse;
import io.dexi.oauth.payloads.OAuthValidateRequest;
import io.dexi.service.exceptions.AccessDeniedException;
import io.dexi.service.handlers.OAuthHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;

@ConditionalOnBean(OAuthHandler.class)
@RestController
@RequestMapping("/oauth/")
public class OAuthController {

    @Autowired
    private OAuthHandler oAuthHandler;

    @Autowired
    private OAuthEncryptionService encryptionService;

    @RequestMapping(value = "/redirect", method = RequestMethod.POST)
    public OAuthRedirectResponse redirect(@RequestBody OAuthRedirectRequest redirectPayload) {

        final URL redirectUrl = oAuthHandler.getRedirectUrl(
                redirectPayload.getState(),
                redirectPayload.getReturnUrl());


        OAuthRedirectResponse out = new OAuthRedirectResponse();
        out.setUrl(redirectUrl.toString());
        return out;
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public EncryptedOAuthTokens validate(@RequestBody OAuthValidateRequest validatePayload) {
        OAuthTokens out = oAuthHandler.validate(
            validatePayload.getCode(),
            validatePayload.getRedirectUrl()
        );

        if (out == null) {
            throw new AccessDeniedException("Failed to authenticate with " + oAuthHandler.getProviderName());
        }

        return encryptionService.encrypt(out);
    }

}

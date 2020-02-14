package io.dexi.service.rest;

import io.dexi.oauth.EncryptedOAuthTokens;
import io.dexi.oauth.OAuthEncryptionService;
import io.dexi.oauth.OAuth2Tokens;
import io.dexi.oauth.payloads.OAuthRedirectRequest;
import io.dexi.oauth.payloads.OAuth2RedirectResponse;
import io.dexi.oauth.payloads.OAuth2ValidateRequest;
import io.dexi.service.exceptions.AccessDeniedException;
import io.dexi.service.handlers.OAuth2Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;

@ConditionalOnBean(OAuth2Handler.class)
@RestController
@RequestMapping("/oauth/")
public class OAuth2Controller {

    @Autowired
    private OAuth2Handler oAuthHandler;

    @Autowired
    private OAuthEncryptionService encryptionService;

    @RequestMapping(value = "/redirect", method = RequestMethod.POST)
    public OAuth2RedirectResponse redirect(@RequestBody OAuthRedirectRequest redirectPayload) {

        final URL redirectUrl = oAuthHandler.getRedirectUrl(
                redirectPayload.getState(),
                redirectPayload.getReturnUrl());


        OAuth2RedirectResponse out = new OAuth2RedirectResponse();
        out.setUrl(redirectUrl.toString());
        return out;
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public EncryptedOAuthTokens validate(@RequestBody OAuth2ValidateRequest validatePayload) {
        OAuth2Tokens out = oAuthHandler.validate(
            validatePayload.getCode(),
            validatePayload.getRedirectUrl()
        );

        if (out == null) {
            throw new AccessDeniedException("Failed to authenticate with " + oAuthHandler.getProviderName());
        }

        return encryptionService.encrypt(out);
    }

}

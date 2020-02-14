package io.dexi.service.rest;

import io.dexi.oauth.EncryptedOAuthTokens;
import io.dexi.oauth.OAuth1Tokens;
import io.dexi.oauth.OAuthEncryptionService;
import io.dexi.oauth.payloads.OAuth1RedirectResponse;
import io.dexi.oauth.payloads.OAuth1ValidateRequest;
import io.dexi.oauth.payloads.OAuthRedirectRequest;
import io.dexi.service.exceptions.AccessDeniedException;
import io.dexi.service.handlers.OAuth1Handler;
import io.dexi.service.handlers.OAuth1RequestToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;

@ConditionalOnBean(OAuth1Handler.class)
@RestController
@RequestMapping("/oauth1/")
public class OAuth1Controller {

    @Autowired
    private OAuth1Handler oAuthHandler;

    @Autowired
    private OAuthEncryptionService encryptionService;

    @RequestMapping(value = "/redirect", method = RequestMethod.POST)
    public OAuth1RedirectResponse redirect(@RequestBody OAuthRedirectRequest redirectPayload) {

        final OAuth1RequestToken requestToken = oAuthHandler.getRequestToken(
            redirectPayload.getReturnUrl()
        );

        final URL redirectUrl = oAuthHandler.getRedirectUrl(
                requestToken.getOauthToken(),
                redirectPayload.getReturnUrl());

        OAuth1RedirectResponse out = new OAuth1RedirectResponse();
        out.setRequestToken(requestToken.getOauthToken());
        out.setUrl(redirectUrl.toString());
        return out;
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public EncryptedOAuthTokens validate(@RequestBody OAuth1ValidateRequest validatePayload) {
        OAuth1Tokens out = oAuthHandler.validate(
            validatePayload.getOauthToken(),
            validatePayload.getOauthVerifier(),
            validatePayload.getRedirectUrl()
        );

        if (out == null) {
            throw new AccessDeniedException("Failed to authenticate with " + oAuthHandler.getProviderName());
        }

        return encryptionService.encrypt(out);
    }

}

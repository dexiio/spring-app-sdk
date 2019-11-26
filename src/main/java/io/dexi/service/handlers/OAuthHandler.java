package io.dexi.service.handlers;

import io.dexi.oauth.OAuthTokens;

import java.net.URL;

public interface OAuthHandler {

    URL getRedirectUrl(String state, String returnUrl);

    OAuthTokens validate(String code, String redirectUrl);

    String getProviderName();

}

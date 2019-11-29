package io.dexi.service.handlers;

import io.dexi.oauth.OAuthTokens;

import java.net.URL;

/**
 * Implement this handler to handle oauth authentication flows
 */
public interface OAuthHandler {

    /**
     * Should return a redirect url for the OAuth2 flow.
     *
     * @param state the state variable (see OAuth2). Should typically be passed on in the redirect url
     * @param returnUrl the url to return the user back to.
     * @return a url to redirect the user to for authentication
     */
    URL getRedirectUrl(String state, String returnUrl);

    /**
     * Validate the code and redirect url returned in the OAuth2 flow.
     *
     * @param code code received from the OAuth provider
     * @param redirectUrl the redirect url previously generated by the getRedirectUrl method for this OAuth authentication
     * @return OAuthTokens that can be used to invoke the oauth provider API
     */
    OAuthTokens validate(String code, String redirectUrl);

    /**
     * Returns a provider name for the specific oauth2 implementation
     *
     * @return a name for the provider
     */
    String getProviderName();

}

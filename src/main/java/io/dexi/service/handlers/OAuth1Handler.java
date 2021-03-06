package io.dexi.service.handlers;

import io.dexi.oauth.OAuth1Tokens;

import java.net.URL;

/**
 * Implement this handler to handle oauth1 authentication flows
 */
public interface OAuth1Handler {

    /**
     * Step 1: Get the request token for initiating the OAuth1 flow
     *
     * Will be called automatically when a new flow is initiated by the user
     *
     * @param returnUrl
     * @return
     */
    OAuth1RequestToken getRequestToken(String returnUrl);

    /**
     * Step 2: Get the redirect url for where to send the user (in the browser)
     *
     *
     * Will be exposed as POST /oauth/redirect
     *
     * @param oauthToken the request token variable retrived b variable (see OAuth1 documentation). Should typically be used in the initial request
     * @param returnUrl the url to return the user back to.
     * @return a url to redirect the user to for authentication
     */
    URL getRedirectUrl(String oauthToken, String returnUrl);

    /**
     * Step3: Validate the tokens and redirect url returned in the OAuth1 flow and return OAuth1 tokens
     *
     * Will be exposed as POST /oauth/validate
     *
     * The tokens will be encrypted before being send to dexi.
     *
     * @param token the token received from the OAuth provider
*      @param verifier the token received from the OAuth provider
     * @param redirectUrl the redirect url previously generated by the getRedirectUrl method for this OAuth authentication
     * @return OAuthTokens that can be used to invoke the oauth provider API
     */
    OAuth1Tokens validate(String token, String verifier, String redirectUrl);

    /**
     * Returns a provider name for the specific oauth1 implementation
     *
     * @return a name for the provider
     */
    String getProviderName();

}

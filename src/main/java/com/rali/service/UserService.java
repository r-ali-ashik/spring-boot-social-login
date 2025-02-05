package com.rali.service;


import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface UserService {
    void handleOidcUser(String provider, OidcUser oidcUser);

    void handleOAuth2User(String provider, OAuth2User oAuth2User);
}

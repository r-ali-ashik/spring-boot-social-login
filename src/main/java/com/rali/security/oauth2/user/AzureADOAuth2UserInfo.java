package com.rali.security.oauth2.user;

import java.util.Map;

public class AzureADOAuth2UserInfo extends OAuth2UserInfo {

    public AzureADOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return ((Integer) attributes.get("id")).toString();
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getFirstName() {
        String[] parts = getName().split(" ");
        return parts[0];
    }

    @Override
    public String getLastName() {
        String[] parts = getName().split(" ");
        return parts[1];
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("picture");
    }

    @Override
    public String getUsername() {
        return (String) attributes.get("preferred_username");
    }
}

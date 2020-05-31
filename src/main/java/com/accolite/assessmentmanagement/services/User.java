package com.accolite.assessmentmanagement.services;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Map;

public class User extends OidcUserService {
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

        System.out.println("INSIDE");
        OidcUser oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String emailFromGoogle = (String) attributes.get("email");

        System.out.println(emailFromGoogle);
        System.out.println("HOOKED");

        return oAuth2User;
    }
}

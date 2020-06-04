package com.accolite.assessmentmanagement.services;

import com.accolite.assessmentmanagement.models.User;
import com.accolite.assessmentmanagement.repository.UserRepository;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class UserService extends OidcUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

        OidcUser oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String sub = (String) attributes.get("sub");

        if(!userRepository.findById(sub).isPresent()){
            userRepository.save(new User(sub, name, email));
        }

        return oAuth2User;
    }

    @Transactional
    public User getUserById(String userId){
        return userRepository.findById(userId).get();
    }
}

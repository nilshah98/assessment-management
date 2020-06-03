package com.accolite.assessmentmanagement.resources;

import com.accolite.assessmentmanagement.models.User;
import com.accolite.assessmentmanagement.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SelfResource {

    private UserRepository userRepository;

    public SelfResource(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/api/self")
    public User user(@AuthenticationPrincipal OAuth2User principal) {
        String userId = (String) principal.getAttribute("sub");
        return userRepository.findById(userId).get();
    }

}

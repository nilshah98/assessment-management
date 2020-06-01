package com.accolite.assessmentmanagement.resources;

import com.accolite.assessmentmanagement.models.User;
import com.accolite.assessmentmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResource {

    private UserRepository userRepository;

    public UserResource(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/api/user/{id}")
    public User getUser(@PathVariable String id){
        System.out.println(id);
        return userRepository.findById(id).get();
    }
}

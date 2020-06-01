package com.accolite.assessmentmanagement.resources;

import com.accolite.assessmentmanagement.models.Course;
import com.accolite.assessmentmanagement.repository.CourseRepository;
import com.accolite.assessmentmanagement.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class CourseResource {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseResource(UserRepository userRepository, CourseRepository courseRepository){
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }


    @GetMapping("/api/courses")
    public List<Course> getCourses(){
        return StreamSupport.stream(courseRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @PostMapping("/api/course")
    public Course saveCourse(@RequestBody Course course, @AuthenticationPrincipal OAuth2User principal){
        System.out.println(course);
        System.out.println((userRepository.findById((String) principal.getAttribute("sub"))).get());
        course.setUser((userRepository.findById((String) principal.getAttribute("sub"))).get());
        return courseRepository.save(course);
    }
}

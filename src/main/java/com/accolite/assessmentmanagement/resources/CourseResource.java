package com.accolite.assessmentmanagement.resources;

import com.accolite.assessmentmanagement.models.Course;
import com.accolite.assessmentmanagement.repository.CourseRepository;
import com.accolite.assessmentmanagement.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/api/course/{id}")
    public ResponseEntity<?> editCourse(@RequestBody Course course, @AuthenticationPrincipal OAuth2User principal){
        System.out.println("Hit");
        String userId = (String) principal.getAttribute("sub");
        if(!course.getUser().getId().equals(userId)){
            return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }else{
            return new ResponseEntity<>(courseRepository.save(course), HttpStatus.OK);
        }
    }

    @DeleteMapping("/api/course/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id, @AuthenticationPrincipal OAuth2User principal){
        String userId = (String) principal.getAttribute("sub");
        Course course = courseRepository.findById(id).get();
        if(!course.getUser().getId().equals(userId)){
            return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }else{
            courseRepository.deleteById(id);
            return new ResponseEntity<String>("Successful", HttpStatus.OK);
        }
    }
}

package com.accolite.assessmentmanagement.resources;

import com.accolite.assessmentmanagement.models.Course;
import com.accolite.assessmentmanagement.services.CourseService;
import com.accolite.assessmentmanagement.services.UnAuthorizedAccessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class CourseResource {

    private final CourseService courseService;

    public CourseResource(CourseService courseService){
        this.courseService = courseService;
    }

    @GetMapping("/api/courses")
    public ResponseEntity<?> getCourses(){
        log.info("[GET] /api/courses getCourses");
        return new ResponseEntity<List<Course>>(courseService.getAllCourses(), HttpStatus.OK);
    }

    @PostMapping("/api/course")
    public ResponseEntity<?> saveCourse(@RequestBody Course course, @AuthenticationPrincipal OAuth2User principal){
        log.info("[POST] /api/course saveCourse");
        String userId = (String) principal.getAttribute("sub");
        return new ResponseEntity<Course>(this.courseService.addUserByIdSaveCourse(userId, course), HttpStatus.CREATED);
    }

    @PutMapping("/api/course/{id}")
    public ResponseEntity<?> editCourse(@RequestBody Course course, @AuthenticationPrincipal OAuth2User principal){
        log.info("[PUT] /api/course/{id} editCourse");
        String userId = (String) principal.getAttribute("sub");
        try{
            Course editedCourse = courseService.checkUserByIdSaveCourse(userId, course);
            return new ResponseEntity<>(editedCourse, HttpStatus.OK);
        }catch(UnAuthorizedAccessException e){
            log.error(String.valueOf(e));
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/api/course/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id, @AuthenticationPrincipal OAuth2User principal){
        log.info("[DELETE] /api/course/{id} deleteCourse");
        String userId = (String) principal.getAttribute("sub");
        try{
            this.courseService.checkUserByIdDeleteCourseById(userId, id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }catch(UnAuthorizedAccessException e){
            log.error(String.valueOf(e));
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}

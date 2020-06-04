package com.accolite.assessmentmanagement.resources;

import com.accolite.assessmentmanagement.models.Course;
import com.accolite.assessmentmanagement.services.CourseService;
import lombok.extern.slf4j.Slf4j;
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
    public List<Course> getCourses(){
        log.info("[GET] /api/courses getCourses");
        return courseService.getAllCourses();
    }

    @PostMapping("/api/course")
    public Course saveCourse(@RequestBody Course course, @AuthenticationPrincipal OAuth2User principal){
        log.info("[POST] /api/course saveCourse");
        String userId = (String) principal.getAttribute("sub");
        return this.courseService.addUserByIdSaveCourse(userId, course);
    }

    @PutMapping("/api/course/{id}")
    public Course editCourse(@RequestBody Course course, @AuthenticationPrincipal OAuth2User principal){
        log.info("[PUT] /api/course/{id} editCourse");
        String userId = (String) principal.getAttribute("sub");
        return courseService.checkUserByIdSaveCourse(userId, course);
//        Ideally should catch error here and return response as ResponseEntity
//        if(!courseService.checkUserByIdSaveCourse(userId, course).getId()){
//            return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
//        }else{
//            return new ResponseEntity<>(courseRepository.save(course), HttpStatus.OK);
//        }
    }

    @DeleteMapping("/api/course/{id}")
    public void deleteCourse(@PathVariable Long id, @AuthenticationPrincipal OAuth2User principal){
        log.info("[DELETE] /api/course/{id} deleteCourse");
        String userId = (String) principal.getAttribute("sub");
        this.courseService.checkUserByIdDeleteCourseById(userId, id);
    }
}

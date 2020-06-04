package com.accolite.assessmentmanagement.services;

import com.accolite.assessmentmanagement.models.Course;
import com.accolite.assessmentmanagement.models.User;
import com.accolite.assessmentmanagement.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserService userService;

    public CourseService(UserService userService, CourseRepository courseRepository){
        this.userService = userService;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public List<Course> getAllCourses(){
        return StreamSupport.stream(courseRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

//    Since CrudRepository handles save and edit, both in save, hence only one method for same
    @Transactional
    public Course saveCourse(Course course){
        return courseRepository.save(course);
    }

    @Transactional
    public Course getCourseById(Long courseId){
//        Further tests needed to check for invalid id calls
        return courseRepository.findById(courseId).get();
    }

    @Transactional
    public void deleteCourseById(Long courseId){
        courseRepository.deleteById(courseId);
    }

    public Course addUserSaveCourse(User user, Course course){
        course.setUser(user);
        return this.saveCourse(course);
    }

    public Course addUserByIdSaveCourse(String userId, Course course){
        User user = this.userService.getUserById(userId);
        return addUserSaveCourse(user, course);
    }

    public Course checkUserByIdSaveCourse(String userId, Course course){
        if(course.getUser().getId().equals(userId)) return this.saveCourse(course);
//        Ideally should throw error here, returning empty course for now
        else return new Course();
    }

    public void checkUserByIdDeleteCourseById(String userId, Long courseId){
        Course course = this.getCourseById(courseId);
        if(course.getUser().getId().equals(userId)) this.deleteCourseById(courseId);
    }
}

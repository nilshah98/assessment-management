package com.accolite.assessmentmanagement.service;

import com.accolite.assessmentmanagement.models.Course;
import com.accolite.assessmentmanagement.models.User;
import com.accolite.assessmentmanagement.repository.CourseRepository;
import com.accolite.assessmentmanagement.services.CourseService;
import com.accolite.assessmentmanagement.services.UnAuthorizedAccessException;
import com.accolite.assessmentmanagement.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserService userService;

    private CourseService courseService;
    private Course course;
    private User user;

    @Before
    public void setUp() throws Exception {
        courseService = new CourseService(userService, courseRepository);
    }



    @Test
    public void getCourseByIdTest(){

        // Creating course with id 114
        course = new Course("New Course","Top course for logging");
        // Creator of course id 114
        user = new User("103797182174759514929", "Neel Shah", "neelshah.1998@gmail.com");

        course.setId(114L);
        course.setUser(user);

        // When
        Mockito.when(courseRepository.findById(114L)).thenReturn(java.util.Optional.ofNullable(course));
        Course newCourse = courseService.getCourseById(114L);

        // Assert
        assertEquals(course, newCourse);
    }

    @Test
    public void authorizedUser_checkUserByIdSaveCourseTest() throws UnAuthorizedAccessException {

        // Creating course with id 114
        course = new Course("New Course Edit Test","Top course for logging");
        // Creator of course id 114
        user = new User("103797182174759514929", "Neel Shah", "neelshah.1998@gmail.com");

        course.setId(114L);
        course.setUser(user);

        // When
        Mockito.when(courseService.saveCourse(course)).thenReturn(course);
        Course newCourse = courseService.checkUserByIdSaveCourse(user.getId(), course);

        // Assert
        assertEquals(course, newCourse);
    }

    @Test(expected = UnAuthorizedAccessException.class)
    public void unAuthorizedUser_checkUserByIdSaveCourseTest() throws UnAuthorizedAccessException {

        // Creating course with id 114
        course = new Course("New Course Edit Test","Top course for logging");
        // Creator of course id 114
        user = new User("103797182174759514929", "Neel Shah", "neelshah.1998@gmail.com");

        course.setId(114L);
        course.setUser(user);

        // No need of mocking here, since exception is thrown before function call
        Course newCourse = courseService.checkUserByIdSaveCourse("107777360892079263531", course);
    }
}

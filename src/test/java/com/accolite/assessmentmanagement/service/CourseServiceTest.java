package com.accolite.assessmentmanagement.service;

import com.accolite.assessmentmanagement.models.Course;
import com.accolite.assessmentmanagement.repository.CourseRepository;
import com.accolite.assessmentmanagement.services.CourseService;
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

    @Before
    public void setUp() throws Exception {
        courseService = new CourseService(userService, courseRepository);
    }

    @Test
    public void getCourseByIdTest(){

        // Creating course with id 94
        course = new Course("New Course","Top course for logging");

        // When
        Mockito.when(courseRepository.findById(114L)).thenReturn(java.util.Optional.ofNullable(course));
        Course newCourse = courseService.getCourseById(114L);

        // Assert
        assertEquals(course.getTitle(), newCourse.getTitle());
        assertEquals(course.getDescription(), newCourse.getDescription());
    }
}

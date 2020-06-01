package com.accolite.assessmentmanagement.repository;

import com.accolite.assessmentmanagement.models.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
}

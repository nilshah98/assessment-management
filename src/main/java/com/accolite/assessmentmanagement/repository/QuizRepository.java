package com.accolite.assessmentmanagement.repository;

import com.accolite.assessmentmanagement.models.Quiz;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends CrudRepository<Quiz, Long> {
}

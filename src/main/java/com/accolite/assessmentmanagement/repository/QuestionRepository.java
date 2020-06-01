package com.accolite.assessmentmanagement.repository;

import com.accolite.assessmentmanagement.models.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {
}

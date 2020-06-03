package com.accolite.assessmentmanagement.repository;

import com.accolite.assessmentmanagement.models.Result;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends CrudRepository<Result, Long> {
}

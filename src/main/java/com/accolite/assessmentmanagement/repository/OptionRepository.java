package com.accolite.assessmentmanagement.repository;

import com.accolite.assessmentmanagement.models.Option;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends CrudRepository<Option, Long> {
}

package com.accolite.assessmentmanagement.resources;

import com.accolite.assessmentmanagement.models.Option;
import com.accolite.assessmentmanagement.models.Question;
import com.accolite.assessmentmanagement.repository.OptionRepository;
import com.accolite.assessmentmanagement.repository.QuestionRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class QuestionResource {

    private QuestionRepository questionRepository;
    private OptionRepository optionRepository;

    public QuestionResource(QuestionRepository questionRepository, OptionRepository optionRepository){
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
    }

    @GetMapping("/test/api/questions")
    public List<Question> getQuestions(){
        return StreamSupport.stream(questionRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @PostMapping(value = "/test/api/question", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Question saveQuestion(@RequestBody Question question){
        question.addOptions(question.getOptions());
        return questionRepository.save(question);
    }

    @PutMapping("/test/api/question/{id}")
    public void editQuestion(@RequestBody Question question, @PathVariable Long id){
//        TODO: Only allow authorized users to make changes to question
    }

}

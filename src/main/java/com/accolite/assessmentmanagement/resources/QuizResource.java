package com.accolite.assessmentmanagement.resources;

import com.accolite.assessmentmanagement.models.Question;
import com.accolite.assessmentmanagement.models.Quiz;
import com.accolite.assessmentmanagement.repository.QuestionRepository;
import com.accolite.assessmentmanagement.repository.QuizRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class QuizResource {

    private QuizRepository quizRepository;
    private QuestionRepository questionRepository;

    public QuizResource(QuizRepository quizRepository, QuestionRepository questionRepository){
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    @GetMapping("/test/api/quizes")
    public List<Quiz> getQuizes(){
        return StreamSupport.stream(quizRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @PostMapping("/test/api/quiz")
    public Quiz saveQuiz(@RequestBody Quiz quiz){
        return quizRepository.save(quiz);
    }

    @PutMapping(value = "/test/api/quiz/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Quiz editQuiz(@RequestBody Map<String, Object> body, @PathVariable Long id){

//        Extract all question Ids
        List<HashMap<String, Long>> Qids = (List<HashMap<String, Long>>) body.get("questions");

//        Extract questions
        Set<Question> questionsToAdd = new HashSet<Question>();
        for (HashMap<String, Long> e: Qids) {
            Long qid = ((Number) e.get("id")).longValue();
            questionsToAdd.add(questionRepository.findById(qid).get());
        }

//        Get Current Quiz
        Quiz currQuiz = quizRepository.findById(id).get();

//        Get existing questions
        Set<Question> currQuestions = currQuiz.getQuestions();

//        Merge questionsToAdd to currQuestions
        for (Question q: questionsToAdd) { currQuestions.add(q); }

//        Make changes to currQuiz
        currQuiz.setTitle((String) body.get("title"));
        currQuiz.setDescription((String) body.get("description"));
        currQuiz.setQuestions(currQuestions);

        return quizRepository.save(currQuiz);

//        TODO: Only allow authorized users to make changes to quiz
    }

}

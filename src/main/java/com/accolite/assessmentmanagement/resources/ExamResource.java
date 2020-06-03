package com.accolite.assessmentmanagement.resources;

import com.accolite.assessmentmanagement.models.Question;
import com.accolite.assessmentmanagement.models.Quiz;
import com.accolite.assessmentmanagement.models.Result;
import com.accolite.assessmentmanagement.repository.QuizRepository;
import com.accolite.assessmentmanagement.repository.ResultRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class ExamResource {

    // Repositories
    private final ResultRepository resultRepository;
    private final QuizRepository quizRepository;

//    Constructor
    public ExamResource(ResultRepository resultRepository, QuizRepository quizRepository){
        this.resultRepository = resultRepository;
        this.quizRepository = quizRepository;
    }

    @GetMapping("/api/exam/{quizId}")
    public Map<String, Object> getExam(@PathVariable Long quizId, @AuthenticationPrincipal OAuth2User principal){
        String userId = (String) principal.getAttribute("sub");
        Result result = null;

//        Check in result, whether userId with the said quizId
        for(Result r : resultRepository.findAll()) {
            if(r.getQuizId() == quizId && r.getUser().getId() == userId){
                result = r;
                break;
            }
        }

//        Check if r is null, if yes, generate questions for given quiz
        if(result == null){
            Quiz quiz = quizRepository.findById(quizId).get();
            Map<String, Object> res =  new HashMap<>();
            res.put("id",quiz.getId());
            res.put("title",quiz.getTitle());
            res.put("description",quiz.getDescription());

            res.put("questions", new ArrayList<Map<String, Object>>());
//            Inserting questions
            for(Question q: quiz.getQuestions()){
                // Creating new object
                Map<String, Object> temp = new HashMap<>();
                temp.put("description", q.getDescription());
                temp.put("options",q.getOptions());

//                Inserting in res
                ((ArrayList<Map<String, Object>>)res.get("questions")).add(temp);
            }

            return res;
        }

        return (Map<String, Object>)result;
    }
}

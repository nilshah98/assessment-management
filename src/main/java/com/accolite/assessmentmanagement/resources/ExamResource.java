package com.accolite.assessmentmanagement.resources;

import com.accolite.assessmentmanagement.models.*;
import com.accolite.assessmentmanagement.repository.QuestionRepository;
import com.accolite.assessmentmanagement.repository.QuizRepository;
import com.accolite.assessmentmanagement.repository.ResultRepository;
import com.accolite.assessmentmanagement.repository.UserRepository;
import com.accolite.assessmentmanagement.services.ExamService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class ExamResource {

//     Repositories
    private final ExamService examService;

//    Constructor
    public ExamResource(ExamService examService){
        this.examService = examService;
    }

    @GetMapping("/api/exams")
    public List<Quiz> getAllExams(){
        return  examService.getAllExams();
    }

    @GetMapping("/api/exam/result/{quizId}")
    public Result getResult(@PathVariable Long quizId, @AuthenticationPrincipal OAuth2User principal){
        String userId = (String) principal.getAttribute("sub");
        return examService.getResultByQuiIdAndUserId(quizId, userId);
    }

    @GetMapping("/api/exam/{quizId}")
    public Quiz getExam(@PathVariable Long quizId, @AuthenticationPrincipal OAuth2User principal){
        String userId = (String) principal.getAttribute("sub");
        return examService.getExamByQuizIdAndUserId(quizId, userId);
    }


    @PostMapping("/api/exam/{quizId}")
    public Result evalExam(@RequestBody Quiz exam, @PathVariable Long quizId, @AuthenticationPrincipal OAuth2User principal){
        String userId = (String) principal.getAttribute("sub");
        return examService.evaluateExam(exam, quizId, userId);

    }
}

package com.accolite.assessmentmanagement.resources;

import com.accolite.assessmentmanagement.models.*;
import com.accolite.assessmentmanagement.repository.QuestionRepository;
import com.accolite.assessmentmanagement.repository.QuizRepository;
import com.accolite.assessmentmanagement.repository.ResultRepository;
import com.accolite.assessmentmanagement.repository.UserRepository;
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
    private final ResultRepository resultRepository;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

//    Constructor
    public ExamResource(ResultRepository resultRepository, QuizRepository quizRepository,
                        QuestionRepository questionRepository, UserRepository userRepository){
        this.resultRepository = resultRepository;
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/api/exams")
    public List<Quiz> getAllExams(){
        List<Quiz> quizes = StreamSupport.stream(quizRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

//        Make each correct option -1
        for(Quiz q: quizes){
            for(Question que: q.getQuestions()){
                que.setCorrectOption(null);
            }
        }
        return quizes;
    }

    @GetMapping("/api/exam/result/{quizId}")
    public Result getResult(@PathVariable Long quizId, @AuthenticationPrincipal OAuth2User principal){
        String userId = (String) principal.getAttribute("sub");
//        Initially no results found
        Result res = new Result(quizId, -1L, -1L);

        for(Result r : resultRepository.findAll()){
            if(r.getQuizId().equals(quizId) && r.getUser().getId().equals(userId)){
                res = r;
                break;
            }
        }
        return res;
    }

    @GetMapping("/api/exam/{quizId}")
    public Quiz getExam(@PathVariable Long quizId, @AuthenticationPrincipal OAuth2User principal){
        String userId = (String) principal.getAttribute("sub");
        boolean userHasTakenTest = false;
        Quiz exam = new Quiz();

//        Check in result, whether userId with the said quizId
        for(Result r : resultRepository.findAll()) {
            if(r.getQuizId().equals(quizId) && r.getUser().getId().equals(userId)){
                exam = new Quiz("Test already taken", "Can take test only once, check results");
                exam.setId(-1L);
                userHasTakenTest = true;
                break;
            }
        }

//        Check if user has not taken exam, generate exam, ie. remove correct option
        if(!userHasTakenTest) {
            exam = quizRepository.findById(quizId).get();
//            Loop through questions and allot correctOption as -1
            for(Question q : exam.getQuestions()){ q.setCorrectOption(null); }
        }

        return exam;
    }


    @PostMapping("/api/exam/{quizId}")
    public Result evalExam(@RequestBody Quiz exam, @PathVariable Long quizId, @AuthenticationPrincipal OAuth2User principal){
//        Extract userId
        String userId = (String) principal.getAttribute("sub");

//        Extract questions
        Set<Question> questions = exam.getQuestions();
//        Get total number of questions
        Long total = (long) questions.size();
//        Now check each answer

        Long score = 0L;
        for (Question q : questions){
//            Get correctOption for question
            Long qid = q.getId();
            Question currQ = questionRepository.findById(qid).get();

//            Note that user input stored in correctOption
//            Take care response is 0 indexed, so need to subtract 1 while checking
            if(currQ.getCorrectOption()-1 == q.getCorrectOption()) score += 1;
        }

        Result result = new Result(quizId, score, total);

//        Get user and add
        User curr = userRepository.findById(userId).get();

//        Set user in results
        result.setUser(curr);

//        Save and return r
        return resultRepository.save(result);

    }
}

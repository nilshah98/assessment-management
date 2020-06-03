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

    // Repositories
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

    @GetMapping("/api/exam/{quizId}")
    public Map<String, Object> getExam(@PathVariable Long quizId, @AuthenticationPrincipal OAuth2User principal){
        String userId = (String) principal.getAttribute("sub");
        Map<String, Object> res =  new HashMap<>();
        boolean userHasTakenTest = false;

//        Check in result, whether userId with the said quizId
        for(Result r : resultRepository.findAll()) {
            if(r.getQuizId().equals(quizId) && r.getUser().getId().equals(userId)){
                res.put("id", r.getId());
                res.put("quizId", r.getQuizId());
                res.put("score", r.getScore());
                res.put("total", r.getTotal());
                userHasTakenTest = true;
            }
        }

//        Check if r is null, if yes, generate questions for given quiz
        if(!userHasTakenTest){
            Quiz quiz = quizRepository.findById(quizId).get();
            res.put("id",quiz.getId());
            res.put("title",quiz.getTitle());
            res.put("description",quiz.getDescription());

            res.put("questions", new ArrayList<Map<String, Object>>());
//            Inserting questions
            for(Question q: quiz.getQuestions()){
                // Creating new object
                Map<String, Object> temp = new HashMap<>();
                temp.put("description", q.getDescription());
                temp.put("id", q.getId());

                // Adding each option
                List<String> ops = new ArrayList<String>();
                for (Option o : (List<Option>) q.getOptions())
                {ops.add(o.getData());}

                temp.put("options",ops);
                // Field to mark users response
                temp.put("response",null);

//                Inserting in res
                ((ArrayList<Map<String, Object>>)res.get("questions")).add(temp);
            }
        }

        return res;
    }

    @PostMapping("/api/exam/{quizId}")
    public Result evalExam(@RequestBody Map<String, Object> body, @PathVariable Long quizId, @AuthenticationPrincipal OAuth2User principal){
//        Extract userId
        String userId = (String) principal.getAttribute("sub");

//        Extract questions
        List<HashMap<String, Long>> questions = (List<HashMap<String, Long>>) body.get("questions");
//        Get total number of questions
        Long total = (long) questions.size();
//        Now check each answer

        Long score = 0L;
        for (HashMap<String, Long> q : questions){
//            Get correctOption for question
            Long qid = ((Number) q.get("id")).longValue();
            Question currQ = questionRepository.findById(qid).get();
            // Take care response is 0 indexed, so need to subtract 1 while checking

            if(currQ.getCorrectOption()-1 == ((Number) q.get("response")).longValue()) score += 1;
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

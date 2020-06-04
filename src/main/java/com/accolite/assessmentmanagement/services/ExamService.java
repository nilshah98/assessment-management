package com.accolite.assessmentmanagement.services;

import com.accolite.assessmentmanagement.models.Question;
import com.accolite.assessmentmanagement.models.Quiz;
import com.accolite.assessmentmanagement.models.Result;
import com.accolite.assessmentmanagement.models.User;
import com.accolite.assessmentmanagement.repository.ResultRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ExamService {

    //     Repositories
    private final ResultRepository resultRepository;
    private final QuizService quizService;
    private final QuestionService questionService;
    private final UserService userService;

    //    Constructor
    public ExamService(ResultRepository resultRepository, QuizService quizService,
                        QuestionService questionService, UserService userService){
        this.resultRepository = resultRepository;
        this.quizService = quizService;
        this.questionService = questionService;
        this.userService = userService;
    }

    public List<Quiz> getAllExams(){
        List<Quiz> quizes = quizService.getAllQuizes();

//        Make each correct option -1, so as to not view in Network tabs
        for(Quiz q: quizes){
            for(Question que: q.getQuestions()){
                que.setCorrectOption(null);
            }
        }
        return quizes;
    }



    @Transactional
    public List<Result> getAllResults() {
        return StreamSupport.stream(resultRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }


    @Transactional
    public Result saveResult(Result result){
        return resultRepository.save(result);
    }



    public Result getResultByQuiIdAndUserId(Long quizId, String userId){
        Result result = new Result(quizId, -1L, -1L);
        for(Result r : getAllResults()){
            if(r.getQuizId().equals(quizId) && r.getUser().getId().equals(userId)){
                result = r;
                break;
            }
        }
        return result;
    }



    public Quiz getExamByQuizIdAndUserId(Long quizId, String userId){
//        If user has already taken exam, return empty quiz
        boolean userHasTakenTest = true;
        Quiz exam = new Quiz();

        Result res = getResultByQuiIdAndUserId(quizId, userId);
        if(res.getScore().equals(-1L)) userHasTakenTest = false;

        if(userHasTakenTest) {
            exam = new Quiz("Test already taken", "Can only take test once");
            exam.setId(-1L);
        }
        else{
            exam = quizService.getQuizById(quizId);
            for(Question q : exam.getQuestions()){ q.setCorrectOption(null); }
        }

        return exam;
    }



    public Result evaluateExam(Quiz exam, Long quizId, String userId){

//        Extract questions
        Set<Question> questions = exam.getQuestions();
//        Get total number of questions
        Long total = (long) questions.size();
//        Now check each answer

        Long score = 0L;
        for (Question q : questions){
//            Get correctOption for question
            Long qid = q.getId();
            Question currQ = questionService.getQuestionById(quizId);

//            Note that user input stored in correctOption
//            Take care response is 0 indexed, so need to subtract 1 while checking
            if(currQ.getCorrectOption()-1 == q.getCorrectOption()) score += 1;
        }

        Result result = new Result(quizId, score, total);

//        Get user and add
        User curr = userService.getUserById(userId);

//        Set user in results
        result.setUser(curr);

//        Save and return r
        return saveResult(result);

    }




}

package com.accolite.assessmentmanagement.services;

import com.accolite.assessmentmanagement.models.Question;
import com.accolite.assessmentmanagement.models.Quiz;
import com.accolite.assessmentmanagement.repository.QuizRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionService questionService;

    public QuizService(QuizRepository quizRepository, QuestionService questionService){
        this.quizRepository = quizRepository;
        this.questionService = questionService;
    }

    @Transactional
    public List<Quiz> getAllQuizes(){
        return StreamSupport.stream(quizRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Transactional
    public Quiz getQuizById(Long quizId){
        return quizRepository.findById(quizId).get();
    }

    @Transactional
    public Quiz saveQuiz(Quiz quiz){
        return quizRepository.save(quiz);
    }

    public Quiz addQuestions(List<Question> questions, Long quizId){
//        Get Current Quiz
        Quiz currQuiz = getQuizById(quizId);
//        Get existing questions
//        Declare as set for no duplicates
        Set<Question> currQuestions = currQuiz.getQuestions();
//        Merge questions to currQuestions
        currQuestions.addAll(questions);
//        Make changes to currQuiz
        currQuiz.setQuestions(currQuestions);
        return saveQuiz(currQuiz);
    }

    public Quiz addQuestionsById(List<Long> questionIds, Long quizId){
//        Get set of all questions
        List<Question> questionsToAdd = new ArrayList<Question>();
        for(Long qid: questionIds){
            questionsToAdd.add(questionService.getQuestionById(qid));
        }
//        AddQuestions
        return addQuestions(questionsToAdd, quizId);
    }
}

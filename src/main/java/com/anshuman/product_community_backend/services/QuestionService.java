package com.anshuman.product_community_backend.services;

import com.anshuman.product_community_backend.dto.QuestionSearchDTO;
import com.anshuman.product_community_backend.model.Question;
import com.anshuman.product_community_backend.model.User;
import com.anshuman.product_community_backend.request.QuestionRequest;

import java.util.List;

public interface QuestionService {

     Question postQuestion(QuestionRequest questionRequest);

     List<Question> getAllQuestions();


     List<Question> searchQuestions(QuestionSearchDTO dto);

     Question getQuestionById(Long id);

     User getCurrentUser();

     Question closeQuestion(Long questionId);

     List<Question> getMyQuestions();



}

package com.anshuman.product_community_backend.services.Impl;

import com.anshuman.product_community_backend.response.StatsResponse;
import com.anshuman.product_community_backend.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import  com.anshuman.product_community_backend.repository.CommentRepository;
import com.anshuman.product_community_backend.repository.QuestionRepository;
import com.anshuman.product_community_backend.repository.userRepository;
import org.springframework.stereotype.Service;


@Service
public class StatsServiceImpl implements StatsService {

    @Autowired
    private userRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CommentRepository commentRepository;
    @Override
    public StatsResponse getStats() {
        long totalUsers = userRepository.count();
        long totalQuestions = questionRepository.count();
        long totalClosedQuestions = questionRepository.countByStatus(true);
        long totalComments = commentRepository.count();
        return new StatsResponse(totalUsers, totalQuestions, totalClosedQuestions, totalComments);
    }
}

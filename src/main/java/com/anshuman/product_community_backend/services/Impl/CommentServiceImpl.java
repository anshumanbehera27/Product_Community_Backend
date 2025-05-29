package com.anshuman.product_community_backend.services.Impl;

import com.anshuman.product_community_backend.model.Comment;
import com.anshuman.product_community_backend.model.Question;
import com.anshuman.product_community_backend.model.User;
import com.anshuman.product_community_backend.repository.CommentRepository;
import com.anshuman.product_community_backend.repository.QuestionRepository;
import com.anshuman.product_community_backend.request.CommentRequest;
import com.anshuman.product_community_backend.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.anshuman.product_community_backend.services.AuthService;
import com.anshuman.product_community_backend.repository.userRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private AuthService authService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private userRepository userRepository;
    @Override
    public Comment postComment(CommentRequest commentRequest) {
        User user = getCurrentUser();

        // Get the actual Question, not Optional
        Question question = questionRepository.findById(commentRequest.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + commentRequest.getQuestionId()));

        // Create and populate the Comment entity
        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        comment.setUser(user); // set managed user
        comment.setDatePosted(LocalDateTime.now());
        comment.setQuestion(question); // set question

        return commentRepository.save(comment);
    }
     @Override
    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authService.getUserByEmail(authentication.getName());
    }


    @Override
    public Comment replyToComment(Long parentCommentId, Comment reply) {
        Comment parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new RuntimeException("Parent comment not found"));

        // Set the parent comment for the reply
        reply.setParentComment(parentComment);

        reply.setQuestion(parentComment.getQuestion());
        reply.setUser(getCurrentUser());
        reply.setDatePosted(LocalDateTime.now());

        // Save and return the reply
        return commentRepository.save(reply);
    }

    @Override
    public Comment approveComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if(comment!=null) {
            comment.setStatus(true);
            return commentRepository.save(comment);
        }
        return null;
    }

    @Override
    public List<Comment> getComments(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new NoSuchElementException("Question not found with id: " + questionId));


        return commentRepository.findByQuestion(question);
    }
}

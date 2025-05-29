package com.anshuman.product_community_backend.services.Impl;

import com.anshuman.product_community_backend.dto.QuestionSearchDTO;
import com.anshuman.product_community_backend.model.Question;
import com.anshuman.product_community_backend.model.User;
import com.anshuman.product_community_backend.repository.QuestionRepository;
import com.anshuman.product_community_backend.request.QuestionRequest;
import com.anshuman.product_community_backend.services.QuestionService;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.anshuman.product_community_backend.services.AuthService;
import com.anshuman.product_community_backend.repository.userRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private AuthService authService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private userRepository userRepository;

    @Autowired
    private EntityManager entityManager;
    @Override
    public Question postQuestion(QuestionRequest questionRequest) {
        Long currentUserId = authService.getCurrentUserId();

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate if the product_code exists in the products table
        String productCode = questionRequest.getProductCode();
        if (productCode != null && !productCode.isEmpty()) {
            boolean productExists = entityManager.createQuery(
                            "SELECT COUNT(p) > 0 FROM Product p WHERE p.productCode = :productCode", Boolean.class)
                    .setParameter("productCode", productCode)
                    .getSingleResult();

            if (!productExists) {
                throw new RuntimeException("Product with code " + productCode + " does not exist");
            }
        }

        Question question = new Question();
        question.setUser(currentUser);
        question.setQuestionTitle(questionRequest.getTitle());
        question.setQuestionContent(questionRequest.getContent());
        question.setLabel(questionRequest.getLabel());
        question.setProductCode(productCode);
        question.setDatePosted(LocalDateTime.now());
        question.setStatus(false);

        return questionRepository.save(question);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();

    }

    @Override
    public List<Question> searchQuestions(QuestionSearchDTO dto) {
        // Extract parameters from DTO
        String title = dto.getTitle();
        String productCode = dto.getProductCode();
        String email = dto.getEmail();
        String label = dto.getLabel();
        LocalDateTime dateFrom = dto.getDateFrom();
        LocalDateTime dateTo = dto.getDateTo();

        // If no parameters provided, return all questions
        if (isAllParametersEmpty(title, productCode, email, label) && dateFrom == null && dateTo == null) {
            dto.setIsDataFound(true);
            return questionRepository.findAll();
        }

        List<Question> result = questionRepository.findAll();

        // Filter by each parameter if provided
        if (title != null && !title.isEmpty()) {
            result = result.stream()
                    .filter(q -> q.getQuestionTitle().toLowerCase().contains(title.toLowerCase()) ||
                            q.getQuestionContent().toLowerCase().contains(title.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (productCode != null && !productCode.isEmpty()) {
            result = result.stream()
                    .filter(q -> q.getProductCode() != null && q.getProductCode().equalsIgnoreCase(productCode))
                    .collect(Collectors.toList());
        }

        if (email != null && !email.isEmpty()) {
            result = result.stream()
                    .filter(q -> q.getUser().getEmail().equalsIgnoreCase(email))
                    .collect(Collectors.toList());
        }

        if (label != null && !label.isEmpty()) {
            result = result.stream()
                    .filter(q -> q.getLabel() != null &&
                            (q.getLabel().equalsIgnoreCase(label) ||
                                    q.getLabel().toLowerCase().contains(label.toLowerCase())))
                    .collect(Collectors.toList());
        }

        // Filter by date range if provided
        if (dateFrom != null) {
            result = result.stream()
                    .filter(q -> q.getDatePosted().isAfter(dateFrom) || q.getDatePosted().isEqual(dateFrom))
                    .collect(Collectors.toList());
        }

        if (dateTo != null) {
            result = result.stream()
                    .filter(q -> q.getDatePosted().isBefore(dateTo) || q.getDatePosted().isEqual(dateTo))
                    .collect(Collectors.toList());
        }

        // Set flag if data was found
        dto.setIsDataFound(!result.isEmpty());

        return result;
    }

    // Helper method to check if all string parameters are empty
    private boolean isAllParametersEmpty(String title, String productCode, String email, String label) {
        return (title == null || title.isEmpty()) &&
                (productCode == null || productCode.isEmpty()) &&
                (email == null || email.isEmpty()) &&
                (label == null || label.isEmpty());
    }

    @Override
    public Question getQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Question not found with id: " + id));
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authService.getUserByEmail(authentication.getName());
    }

    @Override
    public Question closeQuestion(Long questionId) {
        Question question = getQuestionById(questionId);
        question.setStatus(true);
        return questionRepository.save(question);
    }

    @Override
    public List<Question> getMyQuestions() {
        User user = getCurrentUser();
        return questionRepository.findByUser(user);
    }

}

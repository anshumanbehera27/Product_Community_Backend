package com.anshuman.product_community_backend.controller;


import com.anshuman.product_community_backend.dto.QuestionSearchDTO;
import com.anshuman.product_community_backend.model.Question;
import com.anshuman.product_community_backend.request.QuestionRequest;
import com.anshuman.product_community_backend.response.QuestionResponse;
import com.anshuman.product_community_backend.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping
    public ResponseEntity<QuestionResponse> postQuestion(@RequestBody QuestionRequest questionRequest) {

        Question savedQuestion = questionService.postQuestion(questionRequest);
        QuestionResponse questionResponse = QuestionResponse.fromEntity(savedQuestion);

        return ResponseEntity.ok(questionResponse);
    }

    @GetMapping
    public ResponseEntity<List<QuestionResponse>> getAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        List<QuestionResponse> responseDTOs = questions.stream()
                .map(QuestionResponse::fromEntity)
                .collect(Collectors.toList());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        System.out.println("Search request received from user: " + currentUserEmail);
        return ResponseEntity.ok(responseDTOs);
    }
    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> getQuestionById(@PathVariable Long id) {
        Question question = questionService.getQuestionById(id);
        QuestionResponse responseDTO = QuestionResponse.fromEntity(question);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchQuestions(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String productCode,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String label,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        System.out.println("Search request received from user: " + currentUserEmail);


        // Create and populate the QuestionSearchDTO
        QuestionSearchDTO searchDTO = new QuestionSearchDTO();
        searchDTO.setTitle(title);
        searchDTO.setProductCode(productCode);
        searchDTO.setEmail(email);
        searchDTO.setLabel(label);
        searchDTO.setDateFrom(dateFrom);
        searchDTO.setDateTo(dateTo);

        // Call the service with the DTO
        List<Question> questions = questionService.searchQuestions(searchDTO);

        // Convert to response DTOs
        List<QuestionResponse> responseDTOs = questions.stream()
                .map(QuestionResponse::fromEntity)
                .collect(Collectors.toList());

        // Create response with data and isDataFound flag
        Map<String, Object> response = new HashMap<>();
        response.put("questions", responseDTOs);
        response.put("isDataFound", searchDTO.getIsDataFound());


        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<QuestionResponse> closeQuestion(@PathVariable Long id) {
        Question closedQuestion = questionService.closeQuestion(id);
        QuestionResponse responseDTO = QuestionResponse.fromEntity(closedQuestion);
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping("/my")
    public ResponseEntity<List<QuestionResponse>> getMyQuestions() {
        List<Question> questions = questionService.getMyQuestions();
        List<QuestionResponse> responseDTOs = questions.stream()
                .map(QuestionResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }
}

package com.anshuman.product_community_backend.response;

import com.anshuman.product_community_backend.model.Question;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Data
@Getter
@Setter
public class QuestionResponse {
    private Long qid;
    private String username;
    private LocalDateTime datePosted;
    private String title;
    private String content;
    private Boolean status;
    private String label;
    private String email;
    private String productCode;
    private String productName;

    public static QuestionResponse fromEntity(Question question) {
        QuestionResponse dto = new QuestionResponse();
        dto.setQid(question.getQId());
        dto.setUsername(question.getUser().getFirstName()+" "+question.getUser().getLastName());
        dto.setDatePosted(question.getDatePosted());
        dto.setTitle(question.getQuestionTitle());
        dto.setContent(question.getQuestionContent());
        dto.setStatus(question.getStatus());
        dto.setLabel(question.getLabel());
        dto.setProductCode(question.getProductCode());
        dto.setEmail(question.getUser().getEmail());
        // add comment details if needed
        if (question.getProduct() != null){
            dto.setProductName(question.getProduct().getProductName());
        } else {
            dto.setProductName("N/A");
        }

        return dto;
    }
}

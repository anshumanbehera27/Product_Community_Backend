package com.anshuman.product_community_backend.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class QuestionRequest {
    private String title;
    @NotBlank(message = "Content must not be blank")
    private String content;
    private String label;
    private String productCode;

    // Used for date-based search
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime datePosted;


}

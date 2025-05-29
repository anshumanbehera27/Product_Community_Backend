package com.anshuman.product_community_backend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class QuestionSearchDTO {
    private String title;
    private String productCode;
    private String email;
    private String label;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private Boolean isDataFound;

}

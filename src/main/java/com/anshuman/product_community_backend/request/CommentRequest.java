package com.anshuman.product_community_backend.request;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CommentRequest {

    private String content;

    private Long questionId;

}

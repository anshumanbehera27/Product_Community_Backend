package com.anshuman.product_community_backend.response;


import com.anshuman.product_community_backend.model.Comment;
import com.anshuman.product_community_backend.model.Like;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Getter
@Setter
public class CommentResponse {
    private Long cid;
    private String username; // Add username field
    private LocalDateTime datePosted;
    private String content;
    private List<CommentResponse> replies;
    private Long parentCommentId;
    private List<Like> likes;
    private boolean status;

    public static CommentResponse fromEntity(Comment comment) {
        CommentResponse dto = new CommentResponse();
        dto.setCid(comment.getCommentId());
        dto.setUsername(comment.getUser().getFirstName()+" "+comment.getUser().getLastName()); // Extract username from User
        dto.setDatePosted(comment.getDatePosted());
        dto.setContent(comment.getContent());
        dto.setStatus(comment.getStatus());


        // Handle replies recursively
        if (comment.getReplies() != null && !comment.getReplies().isEmpty()) {
            dto.setReplies(comment.getReplies().stream()
                    .map(CommentResponse::fromEntity)
                    .collect(Collectors.toList()));
        } else {
            dto.setReplies(new ArrayList<>());
        }

        // Set parent comment ID if exists
        if (comment.getParentComment() != null) {
            dto.setParentCommentId(comment.getParentComment().getCommentId());
        }

        // Set likes
        dto.setLikes(comment.getLikes());

        return dto;
    }
    public static List<CommentResponse> fromEntityList(List<Comment> comments) {
        return comments.stream()
                .map(CommentResponse::fromEntity)
                .collect(Collectors.toList());
    }
}

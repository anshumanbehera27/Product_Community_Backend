package com.anshuman.product_community_backend.controller;


import com.anshuman.product_community_backend.model.Comment;
import com.anshuman.product_community_backend.request.CommentRequest;
import com.anshuman.product_community_backend.response.CommentResponse;
import com.anshuman.product_community_backend.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @PostMapping
    public ResponseEntity<CommentResponse> postComment(@RequestBody CommentRequest commentRequest) {
        Comment savedComment = commentService.postComment(commentRequest);
        CommentResponse responseDTO = CommentResponse.fromEntity(savedComment);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long questionId) {
        List<Comment> comments = commentService.getComments(questionId);
        List<CommentResponse> responseDTOs = CommentResponse.fromEntityList(comments);
        return ResponseEntity.ok(responseDTOs);
    }

    // Reply to a comment
    @PostMapping("/reply/{parentCommentId}")
    public ResponseEntity<CommentResponse> replyToComment(
            @PathVariable Long parentCommentId,
            @RequestBody Comment reply) {
        Comment savedReply = commentService.replyToComment(parentCommentId, reply);
        CommentResponse responseDTO = CommentResponse.fromEntity(savedReply);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
    @PutMapping("/{commentId}/approve")
    public ResponseEntity<CommentResponse> approveComment(@PathVariable Long commentId) {
        Comment approvedComment = commentService.approveComment(commentId);
        CommentResponse responseDTO = CommentResponse.fromEntity(approvedComment);
        return ResponseEntity.ok(responseDTO);
    }

}

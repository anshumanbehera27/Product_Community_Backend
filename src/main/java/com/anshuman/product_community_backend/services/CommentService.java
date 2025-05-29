package com.anshuman.product_community_backend.services;

import com.anshuman.product_community_backend.model.Comment;
import com.anshuman.product_community_backend.model.User;
import com.anshuman.product_community_backend.request.CommentRequest;

import java.util.List;

public interface CommentService {

    Comment postComment(CommentRequest commentRequest);

    Comment replyToComment(Long parentCommentId, Comment reply);

    Comment approveComment(Long commentId);

    List<Comment> getComments(Long questionId);

    User getCurrentUser();



}

package com.anshuman.product_community_backend.repository;

import com.anshuman.product_community_backend.model.Comment;
import com.anshuman.product_community_backend.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment ,Long> {

    List<Comment> findByQuestion(Question question);
}

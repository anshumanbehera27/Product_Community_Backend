package com.anshuman.product_community_backend.repository;

import com.anshuman.product_community_backend.model.Question;
import com.anshuman.product_community_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long> {

     List<Question> findByUserId(Long userId);
     List<Question> findByUser(User user);

     Long countByStatus(boolean status);


}






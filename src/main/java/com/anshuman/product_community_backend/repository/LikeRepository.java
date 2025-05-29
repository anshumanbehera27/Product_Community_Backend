package com.anshuman.product_community_backend.repository;

import com.anshuman.product_community_backend.model.Comment;
import com.anshuman.product_community_backend.model.Like;
import com.anshuman.product_community_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> findByComment(Comment comment);


    List<Like> findByUser(User user);


    Optional<Like> findByUserAndComment(User user, Comment comment);


    long countByComment(Comment comment);

    // Count likes for multiple comments (useful for bulk operations)
    @Query("SELECT l.comment.id, COUNT(l) FROM Like l WHERE l.comment.id IN :commentIds GROUP BY l.comment.id")
    List<Object[]> countLikesByCommentIds(@Param("commentIds") List<Long> commentIds);


}

package com.anshuman.product_community_backend.services.Impl;

import com.anshuman.product_community_backend.model.Comment;
import com.anshuman.product_community_backend.model.Like;
import com.anshuman.product_community_backend.model.User;
import com.anshuman.product_community_backend.repository.CommentRepository;
import com.anshuman.product_community_backend.repository.LikeRepository;
import com.anshuman.product_community_backend.services.LikeService;
import com.anshuman.product_community_backend.services.AuthService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.anshuman.product_community_backend.repository.userRepository;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AuthService userService;

    @Autowired
    private userRepository userRepository;


    @Transactional
    @Override
    public boolean toggleLike(Long commentId) {
        User currentUser = getCurrentUser();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Comment not found with id: " + commentId));

        // Check if user already liked this comment
        return likeRepository.findByUserAndComment(currentUser, comment)
                .map(like -> {
                    // User already liked this comment, so unlike it
                    likeRepository.delete(like);
                    return false; // Return false to indicate comment is now unliked
                })
                .orElseGet(() -> {
                    // User hasn't liked this comment yet, so like it
                    Like newLike = new Like();
                    newLike.setUser(currentUser);
                    newLike.setComment(comment);
                    likeRepository.save(newLike);
                    return true; // Return true to indicate comment is now liked
                });
    }

    @Override
    public long getLikeCount(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Comment not found with id: " + commentId));
        return likeRepository.countByComment(comment);
    }

    @Override
    public Map<Long, Long> getLikeCounts(List<Long> commentIds) {
        List<Object[]> results = likeRepository.countLikesByCommentIds(commentIds);

        return results.stream()
                .collect(Collectors.toMap(
                        result -> (Long) result[0],  // comment ID
                        result -> (Long) result[1]   // like count
                ));
    }

    @Override
    public boolean hasUserLikedComment(Long commentId) {
        try {
            User currentUser = getCurrentUser();
            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(() -> new NoSuchElementException("Comment not found with id: " + commentId));

            return likeRepository.findByUserAndComment(currentUser, comment).isPresent();
        } catch (Exception e) {
            // If there's any error (like user not authenticated), return false
            return false;
        }
    }

    @Override
    public List<Like> getLikesByComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Comment not found with id: " + commentId));
        return likeRepository.findByComment(comment);
    }

    @Override
    public List<Like> getLikesByUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return likeRepository.findByUser(user);
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.getUserByEmail(authentication.getName());
    }
}

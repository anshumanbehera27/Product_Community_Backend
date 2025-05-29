package com.anshuman.product_community_backend.services;

import com.anshuman.product_community_backend.model.Like;
import com.anshuman.product_community_backend.model.User;

import java.util.List;
import java.util.Map;

public interface LikeService {
    boolean toggleLike(Long commentId);

    long getLikeCount(Long commentId);

    Map<Long, Long> getLikeCounts(List<Long> commentIds);

    boolean hasUserLikedComment(Long commentId);

    List<Like> getLikesByComment(Long commentId);

    List<Like> getLikesByUser(Long userId);

    User getCurrentUser();
}

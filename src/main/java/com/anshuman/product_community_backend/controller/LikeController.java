package com.anshuman.product_community_backend.controller;


import com.anshuman.product_community_backend.model.Like;
import com.anshuman.product_community_backend.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/toggle/{commentId}")
    public ResponseEntity<Map<String, Object>> toggleLike(@PathVariable Long commentId) {
        boolean isLiked = likeService.toggleLike(commentId);
        long likeCount = likeService.getLikeCount(commentId);

        Map<String, Object> response = new HashMap<>();
        response.put("liked", isLiked);
        response.put("likeCount", likeCount);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/count/{commentId}")
    public ResponseEntity<Map<String, Long>> getLikeCount(@PathVariable Long commentId) {
        long count = likeService.getLikeCount(commentId);

        Map<String, Long> response = new HashMap<>();
        response.put("likeCount", count);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/counts")
    public ResponseEntity<Map<Long, Long>> getLikeCounts(@RequestBody List<Long> commentIds) {
        Map<Long, Long> counts = likeService.getLikeCounts(commentIds);
        return ResponseEntity.ok(counts);
    }

    @GetMapping("/status/{commentId}")
    public ResponseEntity<Map<String, Boolean>> hasUserLikedComment(@PathVariable Long commentId) {
        boolean hasLiked = likeService.hasUserLikedComment(commentId);

        Map<String, Boolean> response = new HashMap<>();
        response.put("liked", hasLiked);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<List<Like>> getLikesByComment(@PathVariable Long commentId) {
        List<Like> likes = likeService.getLikesByComment(commentId);
        return ResponseEntity.ok(likes);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Like>> getLikesByUser(@PathVariable Long userId) {
        List<Like> likes = likeService.getLikesByUser(userId);
        return ResponseEntity.ok(likes);
    }


}

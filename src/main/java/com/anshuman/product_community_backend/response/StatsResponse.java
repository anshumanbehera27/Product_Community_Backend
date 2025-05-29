package com.anshuman.product_community_backend.response;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class StatsResponse {
    private long totalUsers;
    private long totalQuestions;
    private long totalClosedQuestions;
    private long totalComments;
    public StatsResponse(long totalUsers, long totalQuestions, long totalClosedQuestions, long totalComments) {
        this.totalUsers = totalUsers;
        this.totalQuestions = totalQuestions;
        this.totalClosedQuestions = totalClosedQuestions;
        this.totalComments = totalComments;
    }
}

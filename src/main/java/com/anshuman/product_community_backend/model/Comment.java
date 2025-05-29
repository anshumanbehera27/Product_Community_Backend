package com.anshuman.product_community_backend.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.LinkOption;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "comments")
@Data
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference("comment-user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    @JsonBackReference("comment-question")
    private Question question;

    @Column(name = "date_posted", nullable = false)
    private LocalDateTime datePosted;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference("comment-replies") // MANAGED SIDE
    private List<Comment> replies;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    @JsonBackReference("comment-replies") // BACK REFERENCE MATCHING MANAGED SIDE
    private Comment parentComment;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Like> likes;

    @Column(name = "status", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean status = false;
}

package com.anshuman.product_community_backend.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "questions")
@Getter
@Setter
public class Question {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long  QId;

    @Column(name = "question_title", nullable = false)
    @NotBlank(message = "Question title is mandatory")
    private String questionTitle;

    @Column(name = "question_content")
//    @NotBlank(message = "Question content is mandatory")
    private String questionContent;

    private String label;
   // connect with product Table
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_code", referencedColumnName = "product_code", insertable = false, updatable = false)
    private Product product;

    @Column(name = "product_code")
    private String productCode;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @Column(name = "date_posted", nullable = false)
    private LocalDateTime datePosted;

    @Column
    private Boolean status = false;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @JsonManagedReference("comment-question") // Add this
    private List<Comment> comments;
}

package com.anshuman.product_community_backend.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ProductId;

     @Column(name = "product_code" ,unique = true , nullable = false)
     private String productCode;

     @Column(name = "product_name")
     private String productName;
}

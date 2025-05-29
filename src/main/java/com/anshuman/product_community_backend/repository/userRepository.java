package com.anshuman.product_community_backend.repository;

import com.anshuman.product_community_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface userRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
}

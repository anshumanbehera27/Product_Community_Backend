package com.anshuman.product_community_backend.security;

import com.anshuman.product_community_backend.model.User;
import com.anshuman.product_community_backend.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
   private userRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>()
        );
    }
    // auth service returns  in the from of the UserDeatils Object to Authenticate properly we need to use this service class
    // this class is used to load the user by username and return the UserDetails object
}

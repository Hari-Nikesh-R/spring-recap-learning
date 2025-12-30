package org.example.javafullstacktraining.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * User Details Service Implementation
 * 
 * This service implements Spring Security's UserDetailsService interface.
 * It is responsible for loading user information during authentication.
 * 
 * Spring Security uses this service to:
 * - Load user details by username
 * - Validate user credentials
 * - Retrieve user authorities/roles
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Loads user details by username.
     * 
     * This method is called by Spring Security during authentication to:
     * 1. Retrieve user information from the database/service
     * 2. Return UserDetails object containing username, password, and authorities
     * 
     * NOTE: This is a simplified implementation. In production, this should:
     * - Query a database or user repository
     * - Return the stored (hashed) password, not encode it each time
     * - Handle multiple users, not just "admin"
     * 
     * @param username The username to load
     * @return UserDetails object containing user information
     * @throws UsernameNotFoundException if user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Simplified user validation - only "admin" user is allowed
        // In production, this should query a database
        if (!username.equals("admin")) {
            throw new UsernameNotFoundException(username);
        }
        
        // NOTE: In production, retrieve the already-hashed password from database
        // Encoding here is just for demonstration - password should be encoded once during registration
        String password = passwordEncoder.encode("password");

        // Build and return UserDetails object with username, password, and roles
        return User.builder()
                .username(username)
                .password(password)
                .roles("ADMIN") // User has ADMIN role
                .build();
    }
}

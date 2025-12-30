package org.example.javafullstacktraining.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Authentication Manager Configuration
 * 
 * This class provides beans for authentication management and password encoding.
 * These beans are used by Spring Security for user authentication.
 */
@Configuration
public class AuthenticationManagerConfig {
    
    /**
     * Creates and configures the AuthenticationManager bean.
     * 
     * AuthenticationManager is responsible for authenticating users based on
     * their credentials (username/password). It delegates to the configured
     * UserDetailsService and PasswordEncoder to validate credentials.
     * 
     * @param authenticationConfiguration Spring's authentication configuration
     * @return AuthenticationManager instance for handling authentication
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Creates a PasswordEncoder bean using BCrypt hashing algorithm.
     * 
     * BCrypt is a strong, adaptive hashing function that:
     * - Automatically handles salt generation
     * - Is resistant to rainbow table attacks
     * - Can adjust computational cost over time
     * 
     * This encoder is used to:
     * - Hash passwords before storing them
     * - Verify passwords during authentication
     * 
     * @return BCryptPasswordEncoder instance for password encoding/decoding
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

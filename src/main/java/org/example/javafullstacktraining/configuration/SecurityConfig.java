package org.example.javafullstacktraining.configuration;

import org.example.javafullstacktraining.filters.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security Configuration
 * 
 * This class configures the security filter chain for the application.
 * It sets up JWT-based authentication, configures public endpoints, and
 * manages session policies.
 */
@Configuration
@EnableMethodSecurity // Enables method-level security annotations like @PreAuthorize
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    /**
     * Configures the security filter chain for HTTP requests.
     * 
     * Security settings:
     * - CSRF protection is disabled (common for stateless JWT-based APIs)
     * - Public endpoints: /store/hello and /store/login (no authentication required)
     * - All other endpoints require authentication
     * - Session management is set to STATELESS (no server-side sessions, uses JWT tokens)
     * - JWT filter is added before the default username/password authentication filter
     * 
     * @param http HttpSecurity object to configure
     * @return SecurityFilterChain with configured security rules
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable) // Disable CSRF for stateless JWT API
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints - no authentication required
                        .requestMatchers("/store/hello", "/store/login")
                        .permitAll()
                        // All other endpoints require authentication
                        .anyRequest()
                        .authenticated())
                // Set session management to STATELESS - no server-side session storage
                // Authentication state is maintained via JWT tokens in each request
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Add JWT filter before the default authentication filter
                // This ensures JWT tokens are validated before other authentication mechanisms
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}


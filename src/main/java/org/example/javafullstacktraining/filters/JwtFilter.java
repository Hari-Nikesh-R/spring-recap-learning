package org.example.javafullstacktraining.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.javafullstacktraining.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT Authentication Filter
 * 
 * This filter intercepts incoming HTTP requests and validates JWT tokens.
 * If a valid JWT token is found in the Authorization header, it extracts
 * user information and sets the authentication in Spring Security context.
 * 
 * Extends OncePerRequestFilter to ensure the filter executes only once per request.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Processes each HTTP request to validate JWT tokens.
     * 
     * Flow:
     * 1. Extracts the Authorization header from the request
     * 2. Checks if it starts with "Bearer " (JWT token format)
     * 3. Extracts and validates the JWT token
     * 4. Loads user details from the token's username
     * 5. Sets the authentication in Spring Security context
     * 6. Continues the filter chain
     * 
     * If no token is present or token is invalid, the request continues
     * without authentication (will be rejected by SecurityConfig if endpoint requires auth).
     * 
     * @param request HTTP servlet request
     * @param response HTTP servlet response
     * @param filterChain Filter chain to continue processing
     * @throws ServletException if servlet error occurs
     * @throws IOException if I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Extract Authorization header
        String authToken = request.getHeader("Authorization");
        
        // Check if Authorization header exists and follows Bearer token format
        if (authToken != null && authToken.startsWith("Bearer ")) {
            // Extract the JWT token (remove "Bearer " prefix)
            String token = authToken.substring(7).trim();
            
            // Extract username from JWT token (validates signature and expiration)
            // This will throw an exception if token is invalid/expired
            String username = jwtUtils.extractUsername(token);
            
            // Load user details from database/service using the username from token
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            // Create authentication object with user details and authorities
            // Credentials are set to null as we're using JWT (stateless authentication)
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            
            // Set authentication in Spring Security context
            // This allows @PreAuthorize and other security annotations to work
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        // Continue the filter chain (pass request to next filter or controller)
        filterChain.doFilter(request, response);
    }
}

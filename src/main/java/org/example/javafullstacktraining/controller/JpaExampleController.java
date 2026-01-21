package org.example.javafullstacktraining.controller;

import org.example.javafullstacktraining.dtos.AuthRequest;
import org.example.javafullstacktraining.model.Student;
import org.example.javafullstacktraining.service.JpaExampleService;
import org.example.javafullstacktraining.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class JpaExampleController {

    // Request -> RestControllerAdvice -> RestController -> Service -> Respository -> Service -> RestController -> RestControllerAdvice

    @Autowired
    private JpaExampleService jpaExampleService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/store/hello")
    public String helloWorld(@RequestParam("q") String id) {
//        throw new NullPointerException();
        return "Hello World!" + id;
    }

    /**
     * Login endpoint for user authentication.
     * 
     * This endpoint handles user login and returns a JWT token upon successful authentication.
     * 
     * Authentication flow:
     * 1. Authenticate user credentials (username/password) using AuthenticationManager
     * 2. Load user details from UserDetailsService
     * 3. Generate and return a JWT token containing user information
     * 
     * The returned JWT token should be included in subsequent requests in the
     * Authorization header as: "Bearer <token>"
     * 
     * @param authRequest Authentication request containing email and password
     * @return JWT token string if authentication succeeds
     * @throws org.springframework.security.core.AuthenticationException if authentication fails
     */
    @PostMapping("/store/login")
    public String login(@RequestBody AuthRequest authRequest) {

        // Step 1: Authenticate user with username and password
        // AuthenticationManager validates credentials against UserDetailsService and PasswordEncoder
        // Throws AuthenticationException if credentials are invalid
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.email, authRequest.password)
        );

        // Step 2: Load user details from UserDetailsService
        // This retrieves the user's authorities/roles needed for token generation
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.email);

        // Step 3: Generate JWT token containing user information
        // Token includes username, roles, and expiration time
        return jwtUtils.generateToken(userDetails);
    }

    @GetMapping("store/new")
    public String newStudent() {
        return "New Student";
    }

    @PostMapping(value = "/store/student")
        public String storeStaticStudentData(@RequestBody Student student) {
        return jpaExampleService.saveStaticStudentData(student);
    }
}

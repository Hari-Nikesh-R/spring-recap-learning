package org.example.javafullstacktraining.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT Utility Class
 * 
 * This utility class handles JWT (JSON Web Token) generation and validation.
 * It uses the jjwt library (version 0.13.0) for token operations.
 * 
 * JWT tokens are used for stateless authentication in the application.
 * Each token contains user information and is signed with a secret key
 * to prevent tampering.
 */
@Component
public class JwtUtils {
    /**
     * Secret key string for signing and verifying JWT tokens.
     * 
     * IMPORTANT: In production, this should be:
     * - Stored in environment variables or secure configuration
     * - At least 256 bits (32 characters) for HS256 algorithm
     * - Kept secret and never committed to version control
     */
    String secretString = "12345678901234567890123456789012";

    /**
     * Secret key derived from the secret string using HMAC SHA-256.
     * 
     * This key is used to:
     * - Sign JWT tokens during generation (ensures integrity)
     * - Verify JWT tokens during validation (prevents tampering)
     * 
     * Keys.hmacShaKeyFor() is the recommended way to create keys for jjwt 0.13.0+
     */
    SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));

    /**
     * Generates a JWT token for the given user.
     * 
     * Token contains:
     * - Subject: username
     * - Roles: user authorities/permissions
     * - Issued at: current timestamp
     * - Expiration: 1 hour from now (3600 seconds)
     * 
     * The token is signed with the secret key to ensure it hasn't been tampered with.
     * 
     * @param userDetails UserDetails object containing user information
     * @return Compact JWT token string (format: header.payload.signature)
     */
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername()) // Set username as subject
                .claim("roles", userDetails.getAuthorities()) // Add user roles/authorities
                .issuedAt(new Date()) // Token creation time
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Expires in 1 hour
                .signWith(key) // Sign token with secret key
                .compact(); // Build and return compact token string
    }

    /**
     * Extracts and validates the username from a JWT token.
     * 
     * This method:
     * 1. Verifies the token signature using the secret key
     * 2. Checks token expiration
     * 3. Extracts the username (subject) from the token payload
     * 
     * If the token is invalid, expired, or tampered with, this method
     * will throw a SignatureException or other JWT-related exception.
     * 
     * Note: Uses verifyWith() method (jjwt 0.13.0+ API) instead of deprecated setSigningKey()
     * 
     * @param token JWT token string to parse
     * @return Username extracted from token subject
     * @throws io.jsonwebtoken.security.SignatureException if signature verification fails
     * @throws io.jsonwebtoken.ExpiredJwtException if token has expired
     * @throws io.jsonwebtoken.JwtException if token is malformed or invalid
     */
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(key) // Verify token signature with secret key
                .build()
                .parseSignedClaims(token) // Parse and validate token
                .getPayload() // Get token payload (claims)
                .getSubject(); // Extract username from subject claim
    }
}

package com.example.demo;

import com.example.demo.services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
    }

    @Test
    void shouldGenerateValidJwtToken() {
        String token = jwtService.generateToken("testuser");

        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void shouldExtractUsernameFromToken() {
        String token = jwtService.generateToken("testuser");

        String username = jwtService.getUsername(token);

        assertEquals("testuser", username);
    }

    @Test
    void shouldExtractUsernameUsingExtractUsernameMethod() {
        String token = jwtService.generateToken("admin");

        String username = jwtService.extractUsername(token);

        assertEquals("admin", username);
    }

    @Test
    void shouldValidateCorrectToken() {
        String token = jwtService.generateToken("testuser");

        boolean isValid = jwtService.validateToken(token);

        assertTrue(isValid);
    }

    @Test
    void shouldReturnFalseForInvalidToken() {
        String invalidToken = "invalid.jwt.token";

        boolean isValid = jwtService.validateToken(invalidToken);

        assertFalse(isValid);
    }

    @Test
    void shouldReturnFalseForTokenWithModifiedSignature() {
        String token = jwtService.generateToken("testuser");

        // портим токен
        String corruptedToken = token.substring(0, token.length() - 2) + "xx";

        boolean isValid = jwtService.validateToken(corruptedToken);

        assertFalse(isValid);
    }
}

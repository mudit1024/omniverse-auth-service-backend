package com.omniverse.auth_Service.service;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final String SECRET = "mysecretkeymysecretkeymysecretkey"; // min 32 chars

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // 🔐 Generate Token
    public String generateToken(Long userId, String email) {

        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 🔍 Extract Email
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // 🔍 Extract UserId
    public Long extractUserId(String token) {
        return getClaims(token).get("userId", Long.class);
    }

    // 🔍 Validate Token
    public boolean isValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
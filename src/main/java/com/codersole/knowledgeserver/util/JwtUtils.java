package com.codersole.knowledgeserver.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey getKey() {
        byte[] keyBates = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBates);
    }

    public String generateToken(Long userId, String username, String role) {
        Date now = new Date();
        Date expireTime = new Date(now.getTime() + expiration);

        return Jwts.builder().subject(String.valueOf(userId)).claim("username", username).claim("role", role)
            .issuedAt(now).expiration(expireTime).signWith(getKey()).compact();
    }

    public Long getUserId(String token) {
        String subject = Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload().getSubject();
        return Long.valueOf(subject);
    }

    public String getRole(String token) {
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

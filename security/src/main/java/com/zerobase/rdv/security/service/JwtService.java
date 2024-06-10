package com.zerobase.rdv.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Component
public class JwtService {
    // 1 day in ms. Should be shorter in production.
    private static final long EXPIRATION_TIME = 86_400_000;
    private static final String PREFIX = "Bearer";
    private static final String KEY_ROLES = "roles";

    // Generate secret key. Only for demonstration purposes.
    // In production, you should read it from the application
    // configuration.
    static final Key key = Keys.secretKeyFor(SignatureAlgorithm.
            HS256);

    // Generate signed JWT token
    public String generateToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(KEY_ROLES, roles);
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + EXPIRATION_TIME);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() +
                        EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    // Get a token from request Authorization header,
    // verify the token, and get username
    public Jws<Claims> getClaims(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null) {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token.replace(PREFIX, ""));
        }
        return null;
    }

    public static String KEY_ROLES() {
        return KEY_ROLES;
    }
}

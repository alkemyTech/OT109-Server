package com.alkemy.ong.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtUtil {

    private static final String SECRET_KEY = "secret";
    private static final String BEARER_PART = "Bearer ";
    private static final String EMPTY = "";

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
    }

    public String generateToken(UserDetails login) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, login.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {


        return  Jwts.builder()
                .setSubject(subject)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))//1 hour
                .signWith(SignatureAlgorithm.HS512,
                        SECRET_KEY.getBytes(StandardCharsets.UTF_8)).compact();

    }

    public String extractUserEmail(String authorizationHeader) {
        String jwtToken = authorizationHeader.replace(BEARER_PART, EMPTY);
        return extractClaim(jwtToken, Claims::getSubject);
    }

}

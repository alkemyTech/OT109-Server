package com.alkemy.ong.util;


import com.alkemy.ong.pojos.output.ResponseLoginDTO;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class JwtUtil {

    private static final String SECRET_KEY = "secret";
    private static final String BEARER_PART = "Bearer ";
    private static final String EMPTY = "";
    private static final String AUTHORITIES = "authorities";

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public String extractId(String token){
        return extractClaim(token, Claims::getId);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
    }

    public String generateToken(ResponseLoginDTO login) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, login.getEmail(), login.getRole());
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private String createToken(Map<String, Object> claims, String subject, String role) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(role);

        return  Jwts.builder()
                .setSubject(subject)
                .claim(AUTHORITIES, grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))//1 hour
                .signWith(SignatureAlgorithm.HS512,
                        SECRET_KEY.getBytes(StandardCharsets.UTF_8)).compact();

    }

    public String extractUserEmail(String authorizationHeader) {
        String jwtToken = authorizationHeader.replace(BEARER_PART, EMPTY);
        return extractClaim(jwtToken, Claims::getSubject);
    }
    public boolean validateJwtToken(String authToken, UserDetails userDetails) {
        final String username = extractUsername(authToken);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(authToken));
    }

}

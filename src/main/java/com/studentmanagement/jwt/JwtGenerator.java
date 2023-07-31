package com.studentmanagement.jwt;

import com.studentmanagement.entity.security.UserEntity;
import com.studentmanagement.repository.security.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtGenerator {
    private final UserRepository userRepository;

    public JwtGenerator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        UserEntity entity = userRepository.findByUsername(username);
        Date currentDate = new Date(System.currentTimeMillis());
        Date expiredDate = new Date(currentDate.getTime() + JwtConstant.JWT_EXPIRATION);
        String token = Jwts.builder()
                .setClaims(Map.of("role",entity.getRoles(),"name",username))
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
        return token;
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JwtConstant.JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JwtConstant.JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JwtConstant.JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

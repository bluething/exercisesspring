package io.github.bluething.spring.security.fundamentaljwt.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public class JwtToken implements Token {

    private final JwtSecret jwtSecret;

    @Autowired
    public JwtToken(JwtSecret jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (60 * 60 * 1000)))
                .signWith(jwtSecret.getRandomKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecret.getRandomKey()).build().parse(token);
            return true;
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            System.out.print(e);
        }
        return false;
    }

    @Override
    public String getPrincipal(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret.getRandomKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}

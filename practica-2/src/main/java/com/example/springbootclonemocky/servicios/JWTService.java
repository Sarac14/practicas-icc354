package com.example.springbootclonemocky.servicios;

import com.example.springbootclonemocky.entidades.MockEndpoint;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTService {
    @Value("${token.jwt}")
    private String SECRET;

    public String generateJwt(MockEndpoint mockEndpoint) {
        String base64EncodedSecretKey = Base64.getEncoder().encodeToString(SECRET.getBytes());
        Claims claims = Jwts.claims().setSubject("subject");
        claims.put("nombre", mockEndpoint.getNombre());
        claims.put("codigo", mockEndpoint.getCodigo());

        Instant instant = mockEndpoint.getExpirationTime().atZone(ZoneId.systemDefault()).toInstant();

        Date date = Date.from(instant);
        return Jwts.builder()
                .setClaims(claims)
                //.setIssuedAt( new Date())
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, base64EncodedSecretKey)
                .compact();
    }
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        String base64EncodedSecretKey = Base64.getEncoder().encodeToString(SECRET.getBytes());
        return Jwts
                .parserBuilder()
                .setSigningKey(base64EncodedSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}

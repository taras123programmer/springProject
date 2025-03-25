package com.ivankiv.schedule;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Component
public class JwtUntil {

    @Value("${jwt.secret}")
    private String secretKey;  // Секретний ключ для підпису токенів

    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60; // Тривалість токену (5 годин)

    // Генерація JWT токену
    public String generateToken(String username) {
        byte[] keyBytes = secretKey.getBytes();
        SecretKey key = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(key)
                .compact();
    }

    // Отримання ім'я користувача з токену
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);  // Використовуємо метод getSubject() з Claims
    }

    // Витягування певних даних з токену
    public <T> T extractClaim(String token, ClaimsExtractor<T> claimsExtractor) {
        final Claims claims = extractAllClaims(token);
        return claimsExtractor.extract(claims);  // Використовуємо власний інтерфейс для витягування даних з claims
    }

    // Витягування всіх даних з токену
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    // Перевірка чи токен прострочений
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Отримання дати закінчення токену
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);  // Використовуємо метод getExpiration() з Claims
    }

    // Перевірка валідності токену
    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    // Власний інтерфейс для витягування даних з Claims
    @FunctionalInterface
    public interface ClaimsExtractor<T> {
        T extract(Claims claims);
    }
}

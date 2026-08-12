package com.taskflow.taskservice.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final String SECRET =
            "UniversalFavoriteAndSuperHandsomeSongjoongkiSecretKey12345";

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                SECRET.getBytes(StandardCharsets.UTF_8)
        );
    }

    public Claims extractClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {

        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);

            return !isTokenExpired(token);

        } catch (ExpiredJwtException e) {
            System.out.println("JWT expired");

        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT");

        } catch (MalformedJwtException e) {
            System.out.println("Malformed JWT");

        } catch (SecurityException e) {
            System.out.println("Invalid signature");

        } catch (IllegalArgumentException e) {
            System.out.println("JWT is empty");
        }

        return false;
    }

    private boolean isTokenExpired(String token) {

        Date expiration =
                extractClaims(token).getExpiration();

        return expiration.before(new Date());
    }
}
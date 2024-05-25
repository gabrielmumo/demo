package dev.gabrielmumo.demo.security;

import dev.gabrielmumo.demo.utils.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtManager {
    @Value("${dev.gabrielmumo.security.jwt-secret}")
    private String secret;

    public String generateTkn(Authentication authentication) {
        String username = authentication.getName();
        Date current = new Date();
        Date expiration = new Date(current.getTime() + Constants.JWT_EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(current)
                .setExpiration(expiration)
                .signWith(getTokenKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsername(String token) {
        return getClaimsFromToken(token, Claims::getSubject);
    }

    public Boolean checkToken(String token) {
        String errorMessage = "";
        try {
            return new Date().before(getClaimsFromToken(token, Claims::getExpiration));
        }  catch (MalformedJwtException e) {
            errorMessage = "Invalid JWT token: " + e.getMessage();
        } catch (ExpiredJwtException e) {
            errorMessage = "JWT token is expired: " + e.getMessage();
        } catch (UnsupportedJwtException e) {
            errorMessage = "JWT token is unsupported: " + e.getMessage();
        } catch (IllegalArgumentException e) {
            errorMessage = "JWT claims string is empty: " + e.getMessage();
        } catch (Exception e) {
            errorMessage = "Unexpected exception: " + e.getMessage();
        }
        // TODO introduce custom exception
        System.err.println(errorMessage);
        return false;
    }

    private Key getTokenKey() {
        byte[] key = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(key);
    }

    private <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getTokenKey()).build().parseClaimsJws(token).getBody();
    }

}

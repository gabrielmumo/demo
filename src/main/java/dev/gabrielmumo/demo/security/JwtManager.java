package dev.gabrielmumo.demo.security;

import dev.gabrielmumo.demo.utils.Constants;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

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
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public Boolean checkToken(String token) {
        String errorMessage = "";
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
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

}

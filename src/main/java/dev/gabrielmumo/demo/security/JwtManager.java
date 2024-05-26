package dev.gabrielmumo.demo.security;

import dev.gabrielmumo.demo.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtManager {

    private static final Log LOG = LogFactory.getLog(JwtManager.class);
    @Value("${dev.gabrielmumo.security.jwt-secret}")
    private String secret;

    @Value("${dev.gabrielmumo.security.expiration-time}")
    private long expirationTime;
    @Value("${dev.gabrielmumo.security.refresh-times}")
    private long refreshTimes;

    public String generateTkn(Authentication authentication) {
        return  generateTkn(authentication, expirationTime);
    }

    public String generateRefreshTkn(Authentication authentication) {
        return  generateTkn(authentication, expirationTime * refreshTimes);
    }

    public String getUsername(String token) {
        return getClaimsFromToken(token, Claims::getSubject);
    }

    public Boolean checkToken(String token) {
        try {
            return new Date().before(getClaimsFromToken(token, Claims::getExpiration));
        }  catch (MalformedJwtException e) {
            LOG.error("Invalid JWT token", e);
        } catch (ExpiredJwtException e) {
            LOG.error("JWT token is expired", e);
        } catch (UnsupportedJwtException e) {
            LOG.error("JWT token is unsupported", e);
        } catch (IllegalArgumentException e) {
            LOG.error("JWT claims string is empty", e);
        } catch (SignatureException e) {
            LOG.error("Invalid JWT signature", e);
        } catch (Exception e) {
            LOG.error("Unexpected exception", e);
        }
        throw new UnauthorizedException("Exception while checking the token");
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

    private String generateTkn(Authentication authentication, long expirationTime) {
        String username = authentication.getName();
        Date current = new Date();
        Date expiration = new Date(current.getTime() + expirationTime);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(current)
                .setExpiration(expiration)
                .signWith(getTokenKey(), SignatureAlgorithm.HS256)
                .compact();
    }

}

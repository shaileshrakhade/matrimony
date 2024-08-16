package com.sr.gateway.util.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Service
public class JwtClaimsImpl implements JwtClaims {

    @Autowired
    private JwtService jwtService;

    @Override
    public String valueFromToken(String token, CustomKeysEnums key) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        final Claims claims = extractAllClaims(token);
        return claims.get(key.name()).toString();
    }

    @Override
    public List<String> extractRoles(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        final Claims claims = extractAllClaims(token);
        List<String> role = (List<String>) claims.get(CustomKeysEnums.ROLE.name());

        return role;
    }

    @Override
    public String extractUsername(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        return extractClaim(token, Claims::getSubject);
    }

    protected <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public boolean isTokenExpired(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return extractExpiration(token).before(new Date());
    }

    @Override
    public boolean isTokenValid(String token) {
        final String username = extractUsername(token);
        return (!username.isEmpty() && !isTokenExpired(token));
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(jwtService.getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

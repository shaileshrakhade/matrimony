package com.matrimony.biodata.util.jwt;

import com.matrimony.biodata.customExceptions.exceptions.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Service
@Slf4j
public class JwtClaimsImpl implements JwtClaims {
    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;
    @Value("${spring.security.application.secret-key}")
    private String applicationSecretKey;

    @Autowired
    private JwtService jwtService;

    @Override
    public String valueFromToken(String token, CustomKeysEnums key) {

        final Claims claims = extractAllClaims(token);
        return claims.get(key.name()).toString();
    }

    @Override
    public List<String> extractRoles(String token) {

        final Claims claims = extractAllClaims(token);
        List<String> role = (List<String>) claims.get(CustomKeysEnums.ROLE.name());

        return role;
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    protected <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    private long getExpirationTime() {
        return jwtExpiration;
    }


    @Override
    public boolean isTokenExpired(String token) {
        log.debug(extractExpiration(token).toString());
        log.debug(new Date().toString());
        return extractExpiration(token).before(new Date());
    }

    @Override
    public boolean isTokenValid(String token) throws InvalidTokenException {
        final String username = extractUsername(token);
        if (!(!username.isEmpty()
                && !isTokenExpired(token)
                && this.valueFromToken(token, CustomKeysEnums.APPLICATION).equals(applicationSecretKey)))
            throw new InvalidTokenException("Token is not valid");
        return true;

    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return Jwts
                .parserBuilder()
                .setSigningKey(jwtService.getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }
}

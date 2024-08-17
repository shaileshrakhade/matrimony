package com.sr.authentication.util.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JwtTokenGenerateImpl implements JwtTokenGenerate {

    @Value("${spring.security.application.secret-key}")
    private String applicationSecretKey;
    @Value("${spring.application.description}")
    private String applicationDescription;
    @Value("${security.jwt.secret-key}")
    private String secretKey;
    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private JwtClaims jwtClaims;

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = jwtClaims.extractUsername(token);
        return (username.equals(userDetails.getUsername())) && jwtClaims.isTokenExpired(token);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put(CustomKeysEnums.APPLICATION.name(), applicationSecretKey);
        extraClaims.put(CustomKeysEnums.APPLICATION_DESCRIPTION.name(), applicationDescription);
        List<String> role = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        extraClaims.put(CustomKeysEnums.ROLE.name(), role);
        return generateToken(extraClaims, userDetails);
    }

    protected String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(jwtService.getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

}

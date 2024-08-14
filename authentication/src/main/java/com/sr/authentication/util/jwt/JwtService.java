package com.sr.authentication.util.jwt;
import org.springframework.security.core.userdetails.UserDetails;


public interface JwtService {
    public String valueFromToken(String token, String value);
    public String extractUsername(String token);
    public String generateToken(UserDetails userDetails);
    public boolean isTokenValid(String token, UserDetails userDetails);
}

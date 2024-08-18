package com.sr.authentication.util.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenGenerate {
    public String generateToken(UserDetails userDetails);
    public boolean isTokenValid(String token, UserDetails userDetails);

}

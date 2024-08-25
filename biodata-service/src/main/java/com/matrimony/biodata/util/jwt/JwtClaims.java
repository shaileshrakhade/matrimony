package com.matrimony.biodata.util.jwt;

import com.matrimony.biodata.customExceptions.exceptions.InvalidTokenException;

import java.util.List;

public interface JwtClaims {
     String valueFromToken(String token, CustomKeysEnums key);
     List<String> extractRoles(String token);
     String extractUsername(String token);
     boolean isTokenExpired(String token);
     public boolean isTokenValid(String token) throws InvalidTokenException;
}
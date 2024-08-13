package com.sr.authentication.service;

import com.sr.authentication.customExceptions.dto.TokenDto;
import com.sr.authentication.customExceptions.exceptions.InvalidTokenException;
import com.sr.authentication.dao.UserCredentialDto;
import com.sr.authentication.dao.UserDetailsDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserOperations {
    public TokenDto register(UserDetailsDto userDetail);

    public String activeUser(UserCredentialDto userCredentialDto, HttpServletRequest request);

    public TokenDto login(UserCredentialDto userCredential);

    public TokenDto refreshToken(String jwtToken);

    public String updatePassword(UserCredentialDto userCredentialDto, HttpServletRequest request);

    public UserDetailsDto updateUser(UserDetailsDto userDetail, HttpServletRequest request);

    public TokenDto forgoPassword(UserCredentialDto userCredentialDto);

    public Boolean deleteUser(Long userId, HttpServletRequest request);



}
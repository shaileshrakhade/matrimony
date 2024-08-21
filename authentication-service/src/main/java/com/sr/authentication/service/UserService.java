package com.sr.authentication.service;

import com.sr.authentication.dao.TokenDto;
import com.sr.authentication.customExceptions.exceptions.PhoneNumberAlreadyExistException;
import com.sr.authentication.customExceptions.exceptions.UsernameAlreadyExistException;
import com.sr.authentication.dao.UserCredentialDto;
import com.sr.authentication.dao.UserDetailsDto;
import com.sr.authentication.enums.Role;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public Optional<UserCredentialDto> getUserCredentialDtoByUserName(String username);

    public List<UserDetailsDto> getAllUser();

    public UserDetailsDto getUserByUserId(Long id);

    public UserDetailsDto getUserByUserName(String username);


    public String activeUser(UserCredentialDto UserCredentialDto);

    public UserDetailsDto updateUser(UserDetailsDto userDetailsDto, String username) throws PhoneNumberAlreadyExistException;

    public TokenDto createUser(UserDetailsDto userDetailsDto) throws UsernameAlreadyExistException;

    public String otpUpdate(String username, String otp);

    public String generateToken(String username);

    public boolean isTokenValid(String token, String username);

    public boolean deleteUser(Long userId, String username);

    public boolean deActiveUser(Long userId, String username);

    public UserDetailsDto markAdmin(Role role, String username);
}
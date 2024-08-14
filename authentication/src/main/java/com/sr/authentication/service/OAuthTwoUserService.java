package com.sr.authentication.service;


import com.sr.authentication.dao.TokenDto;
import com.sr.authentication.customExceptions.exceptions.UsernameAlreadyExistException;
import com.sr.authentication.dao.UserDetailsDto;
import com.sr.authentication.enums.Provider;
import com.sr.authentication.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.oauth2.core.oidc.AddressStandardClaim;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

@Service
public class OAuthTwoUserService {

    @Autowired
    @Lazy
    private  UserService userService;

    public TokenDto createUserGoogleOAuth(DefaultOidcUser defaultOidcUser) throws UsernameAlreadyExistException {
        UserDetailsDto userDetailsDto = UserDetailsDto.builder()
                .userName(defaultOidcUser.getEmail())
                .fullName(defaultOidcUser.getFullName())
                .emailId(defaultOidcUser.getEmail())
                .phoneNumber(defaultOidcUser.getPhoneNumber())
                .provider(Provider.GOOGLE)
                .roles(Role.USER)
                .build();
        return userService.createUser(userDetailsDto);

    }
}

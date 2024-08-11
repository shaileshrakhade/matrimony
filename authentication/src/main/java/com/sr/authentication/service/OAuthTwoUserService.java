package com.sr.authentication.service;


import com.sr.authentication.customExceptions.dto.TokenDto;
import com.sr.authentication.dao.UserDetailsDto;
import com.sr.authentication.enums.Provider;
import com.sr.authentication.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.oauth2.core.oidc.AddressStandardClaim;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class OAuthTwoUserService {

    @Autowired
    @Lazy
    private  UserService userService;

    public TokenDto createUserGoogleOAuth(DefaultOidcUser defaultOidcUser) throws SQLException {
        UserDetailsDto userDetailsDto = UserDetailsDto.builder()
                .userName(defaultOidcUser.getEmail())
                .fullName(defaultOidcUser.getFullName())
                .emailId(defaultOidcUser.getEmail())
                .birthDate(defaultOidcUser.getBirthdate())
                .phoneNumber(defaultOidcUser.getPhoneNumber())
                .gender(defaultOidcUser.getGender())
                .provider(Provider.GOOGLE)
                .address(mapToAddress(defaultOidcUser.getAddress()))
                .roles(Role.USER)
                .build();
        return userService.createUser(userDetailsDto);

    }

    private String mapToAddress(AddressStandardClaim address) {

        StringBuilder addr = new StringBuilder();
        addr.append(address.getStreetAddress());
        addr.append(address.getRegion());
        addr.append(address.getCountry());
        addr.append(address.getPostalCode());
        return addr.toString();

    }
}

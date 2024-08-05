package com.lagn.authentication.service;


import com.lagn.authentication.enums.Provider;
import com.lagn.authentication.model.Address;
import com.lagn.authentication.model.OAuth2Users;
import com.lagn.authentication.repo.OAuth2UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.oidc.AddressStandardClaim;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;

@Service
@AllArgsConstructor
public class OAuth2UserService {

    private static final Logger log = LoggerFactory.getLogger(OAuth2UserService.class);
    private final OAuth2UserRepository oAuth2UserRepository;

    public OAuth2Users createUserOAuthPostLogin(DefaultOidcUser defaultOidcUser) throws SQLException {
        if (defaultOidcUser.getEmail().isEmpty() || defaultOidcUser.getEmail().isBlank())
            throw new SQLException("email is required");
        OAuth2Users oAuth2Users = oAuth2UserRepository.findByEmail(defaultOidcUser.getEmail())
                .stream().findFirst().orElseGet(() ->
                {
                    return OAuth2Users.builder()
                            .email(defaultOidcUser.getEmail())
                            .name(defaultOidcUser.getAttribute("name"))
                            .provider(Provider.GOOGLE)
                            .birthdate(defaultOidcUser.getBirthdate())
//                            .address(mapToAdress(defaultOidcUser.getAddress()))
                            .fullName(defaultOidcUser.getFullName())
                            .gender(defaultOidcUser.getGender())
                            .phoneNumber(defaultOidcUser.getPhoneNumber())
                            .build();
                });
        oAuth2Users.setLastlogin(new Date());
        log.info(defaultOidcUser.toString());
     return    oAuth2UserRepository.save(oAuth2Users);
    }

    private Address mapToAddress(AddressStandardClaim address) {
        return Address.builder()
                .country(address.getCountry())
                .formatted(address.getFormatted())
                .postalCode(address.getPostalCode())
                .region(address.getRegion())
                .streetAddress(address.getStreetAddress())
                .build();
    }
}

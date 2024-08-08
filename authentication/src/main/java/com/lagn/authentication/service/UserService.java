package com.lagn.authentication.service;

import com.lagn.authentication.customExceptions.dto.TokenDto;
import com.lagn.authentication.dao.UserCredentialDto;
import com.lagn.authentication.dao.UserDetailsDto;
import com.lagn.authentication.model.Users;
import com.lagn.authentication.repo.UserRepository;
import com.lagn.authentication.securityConfig.service.CustomUserDetailsService;
import com.lagn.authentication.util.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public Optional<UserCredentialDto> getUserByUserName(String username) {
        return userRepository.findByUserName(username).map(user -> {
            return UserCredentialDto.builder().userName(user.getUserName()).password(user.getPassword()).build();
        });
    }

    public Users passwordUpdate(UserCredentialDto UserCredentialDto) {
        Optional<Users> useroptional = userRepository.findByUserName(UserCredentialDto.getUserName());
        if (useroptional.isPresent()) {
            Users user = useroptional.get();
            user.setPassword(passwordEncoder.encode(UserCredentialDto.getPassword()));
            return userRepository.save(user);

        } else {
            throw new UsernameNotFoundException("User Name not exist");
        }
    }

    public Users updateUser(UserDetailsDto userDetailsDto) {

        Optional<Users> usersOptional = userRepository.findByUserName(userDetailsDto.getUserName());
        if (usersOptional.isPresent()) {
            Users user = usersOptional.get();

            user.setUserName(userDetailsDto.getUserName());
            user.setEmailId(userDetailsDto.getEmailId());
            user.setPhoneNumber(userDetailsDto.getPhoneNumber());
            user.setFullName(userDetailsDto.getFullName());
            user.setWhatsAppNo(userDetailsDto.getWhatsAppNo());
            user.setBirthDate(userDetailsDto.getBirthDate());
            user.setAddress(userDetailsDto.getAddress());
            user.setGender(userDetailsDto.getGender());
            user.setUpdateOn(new Date());
            return userRepository.save(user);
        } else {
            throw new UsernameNotFoundException("User Name not exist");
        }

    }

    public TokenDto createUser(UserDetailsDto userDetailsDto) throws SQLException {
//        if (userDetailsDto.getPhoneNumber().length() < 10) throw new SQLException("Mobile Number is not valid");
//        if (userDetailsDto.getEmailId().isEmpty() || userDetailsDto.getEmailId().isBlank())
//            throw new SQLException("Email is required");
        if (userRepository.findByUserName(userDetailsDto.getUserName()).isPresent()) {
            throw new SQLException("Duplicate entry : account is already exist");
        } else {
            Users user = Users.builder()
                    .userName(userDetailsDto.getUserName())
                    .emailId(userDetailsDto.getEmailId())
                    .phoneNumber(userDetailsDto.getPhoneNumber())
                    .fullName(userDetailsDto.getFullName())
                    .whatsAppNo(userDetailsDto.getWhatsAppNo())
                    .birthDate(userDetailsDto.getBirthDate())
                    .address(userDetailsDto.getAddress())
                    .gender(userDetailsDto.getGender())
                    .provider(userDetailsDto.getProvider())
                    .registerOn(new Date())
                    .build();
            Users users = userRepository.save(user);
            TokenDto tokenDto = new TokenDto();
            tokenDto.setToken(this.generateToken(users.getUserName()));
            tokenDto.setMessage("User Registered please set the password");
            return tokenDto;
        }
    }


    public String generateToken(String username) {

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("project", "marriage");
        String token = jwtService.generateToken(extraClaims, customUserDetailsService.loadUserByUsername(username));
        Optional<Users> useroptional = userRepository.findByUserName(username);
        if (useroptional.isPresent()) {
            Users user = useroptional.get();
            user.setTokenGeneratorOn(new Date());
            userRepository.save(user);
        }
        return token;
    }

    public boolean validateToken(String token) {
        if (jwtService.isTokenExpired(token))
            return getUserByUserName(jwtService.extractUsername(token)).isPresent();
        return false;
    }

    public String valueFromToken(String token, String key) {

//        final String userEmail = jwtService.extractUsername(jwt);
        if (jwtService.isTokenExpired(token))
            return jwtService.extractClaim(token, Claims::getSubject);
        return null;
    }

}

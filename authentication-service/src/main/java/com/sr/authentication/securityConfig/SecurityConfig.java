package com.sr.authentication.securityConfig;


import com.sr.authentication.dao.TokenDto;
import com.sr.authentication.customExceptions.exceptions.UsernameAlreadyExistException;
import com.sr.authentication.enums.Role;
import com.sr.authentication.securityConfig.authenticationFilter.CusomeAuthenticationFilter;
import com.sr.authentication.securityConfig.service.CustomUserDetailsService;
import com.sr.authentication.service.OAuthTwoUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    @Autowired
    private CusomeAuthenticationFilter cusomeAuthenticationFilter;
    @Autowired
    private OAuthTwoUserService oAuthTwoUserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                                auth.requestMatchers("/user/**", "/openapi/**").permitAll()
                                        .requestMatchers("/admin/**").hasAuthority(Role.ADMIN.toString())
//                                .requestMatchers("/mrg/**").authenticated()
                                        .anyRequest().authenticated()
                );

//        httpSecurity.oauth2Login(Customizer.withDefaults());
        httpSecurity.oauth2Login(a -> {
            a.userInfoEndpoint(b -> {

            }).successHandler(new AuthenticationSuccessHandler() {
                @Override
                public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                    Authentication authentication) throws IOException {

                    DefaultOidcUser oauthUser = (DefaultOidcUser) authentication.getPrincipal();
                    TokenDto tokenDto = null;
                    if (response.getStatus() == HttpStatus.OK.value()) {
                        try {
                            tokenDto = oAuthTwoUserService.createUserGoogleOAuth(oauthUser);
                            log.info("token from google :: {}", tokenDto.getToken());
                        } catch (UsernameAlreadyExistException e) {
                            response.sendRedirect("/openapi/error?error" + e.getMessage());
                        }
                        response.sendRedirect("/user/validate-token?token=" + tokenDto.getToken());
                    }
                }
            });
        });

        httpSecurity.addFilterBefore(cusomeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // we create the Bean of AuthenticationManager it was use in UserController to validate the user
    // by using authenticate() method from AuthenticationManager we can validate the user is valid or not.
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //UserDetailsService it's default interface in spring security to loads user-specific data.
    //so we customize it & pass the data so spring security not use default data when we create this Bean.
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


}

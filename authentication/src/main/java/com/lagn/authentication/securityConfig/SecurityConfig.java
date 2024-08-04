package com.lagn.authentication.securityConfig;

import com.lagn.authentication.securityConfig.service.CustomUserDetailsService;
import com.lagn.authentication.service.OAuth2UserService;
import com.lagn.authentication.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
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

import java.io.IOException;
import java.sql.SQLException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Autowired
//    private CustomOAuth2UserService oauthUserService;
    @Autowired
    private OAuth2UserService oAuth2UserService;

    //by default spring security secure all the urls of the projects so here we customize some URLS
    // to give the public access i.e not ask for username & password
    //those request start with [/auth/**] so spring security give the access directly
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(a -> a.requestMatchers("/login/**").permitAll()
                        .anyRequest().authenticated());

        httpSecurity.logout(logoutForm -> {
            logoutForm.logoutUrl("/do-logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });
        httpSecurity.formLogin(Customizer.withDefaults());
        httpSecurity.oauth2Login(a -> {
            a.userInfoEndpoint(b -> {
            }).successHandler(new AuthenticationSuccessHandler() {
                @Override
                public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                    Authentication authentication) throws IOException, ServletException {

                    DefaultOidcUser oauthUser = (DefaultOidcUser) authentication.getPrincipal();

                    try {
                        oAuth2UserService.createUserOAuthPostLogin(oauthUser);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    response.sendRedirect("/secure");
                }
            });
        });
        return httpSecurity.build();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    //spring use password encoder to store the password,
    // so we need to create bean to use password encoding to storing in DB
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //UserDetailsService it's default interface in spring security to loads user-specific data.
    //so we customize it & pass the data so spring security not use default data when we create this Bean.
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    //
//    // we create the Bean of AuthenticationManager it was use in UserController to validate the user
//    // by using authenticate() method from AuthenticationManager we can validate the user is valid or not.
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

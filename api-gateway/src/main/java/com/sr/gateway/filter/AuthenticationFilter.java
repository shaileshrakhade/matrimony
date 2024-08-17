package com.sr.gateway.filter;

import com.sr.gateway.customExceptions.exceptions.InvalidTokenException;
import com.sr.gateway.customExceptions.exceptions.UserTokenNotValid;
import com.sr.gateway.enums.Role;
import com.sr.gateway.util.jwt.CustomKeysEnums;
import com.sr.gateway.util.jwt.JwtClaims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.List;

//creating AuthenticationFilter for Gateway custom class to add authentication on another microservice
@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;
    @Autowired
    private JwtClaims jwtClaims;

    //this method is call when you pass the AuthenticationFilter call name as filter in properties
    // to specific microservice i.e.[spring.cloud.gateway.routes[0].filters=AuthenticationFilter]
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Missing Authorization Header");
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {

                    //REST call to AUTH service to validate token
                    //  template.getForObject("http://AUTHENTICATION-SERVICE//validate?token" + authHeader, String.class);
                    // here we directly create the same class to validate token on the basic of secret key only
                    //if you want to check in DB as well is this validate username then we need to call the auth request
                    if (!jwtClaims.isTokenValid(authHeader) &&
                            jwtClaims.valueFromToken(authHeader, CustomKeysEnums.APPLICATION).isEmpty())
                        throw new RuntimeException("Token Information Is Not Valid.");

                    if (routeValidator.isAdmin.test(exchange.getRequest())) {
                        List<String> list = jwtClaims.extractRoles(authHeader);
                        if (!list.contains(Role.ADMIN.name()))
                            throw new RuntimeException("Unauthorized request");
                    }

                }
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {

    }

    public AuthenticationFilter() {
        super(Config.class);
    }


}

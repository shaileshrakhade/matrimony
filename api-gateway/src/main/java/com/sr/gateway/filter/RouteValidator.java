package com.sr.gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/user/",
            "/eureka"
    );
    public static final List<String> adminApiEndpoints = List.of(
            "/admin/"
    );


    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

    public Predicate<ServerHttpRequest> isAdmin =
            request -> adminApiEndpoints
                    .stream()
                    .anyMatch(uri -> request.getURI().getPath().contains(uri));
}

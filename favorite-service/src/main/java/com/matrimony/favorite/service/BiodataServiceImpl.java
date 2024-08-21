package com.matrimony.favorite.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class BiodataServiceImpl implements BiodataService {

    @Autowired
    @Qualifier("webClientBuilderBiodata")
    private WebClient.Builder webClientBuilderBiodata;


    @Override
    public boolean isPresent(HttpServletRequest request, String biodataId) {
        log.debug(Thread.currentThread().getName());
        return Boolean.TRUE.equals(webClientBuilderBiodata.build()
                .get()
                .uri("/bio-data/is-present/" + biodataId)
                .header(HttpHeaders.AUTHORIZATION, request.getHeader(HttpHeaders.AUTHORIZATION))
                .retrieve().bodyToMono(Boolean.class).block());
    }
}

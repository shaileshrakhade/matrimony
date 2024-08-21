package com.matrimony.favorite.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class WebClientConfig {
    @Value("${matrimony.micro.biodata.base.url}")
    private String biodataBaseUrl;

    @Bean(name = "webClientBuilderBiodata")
    @LoadBalanced
//    bean use for API call
    public WebClient.Builder webClientBuilderBiodata() {
        String baseUrl = biodataBaseUrl;
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl);
        return WebClient.
                builder()
                .uriBuilderFactory(factory);
    }

}

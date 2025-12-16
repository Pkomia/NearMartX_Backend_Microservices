package com._Rbrothers.order_service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced  //load balancer from client side 
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }
}

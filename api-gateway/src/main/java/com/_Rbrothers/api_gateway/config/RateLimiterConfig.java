package com._Rbrothers.api_gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class RateLimiterConfig {

    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> {
            // rate limter based on user
            String email = exchange.getRequest()
                    .getHeaders()
                    .getFirst("X-User-Email");

            if (email != null) {
                return Mono.just(email);
            }

            // fallback to IP address
            return Mono.just(
                    exchange.getRequest()
                            .getRemoteAddress()
                            .getAddress()
                            .getHostAddress()
            );
        };
    }
}
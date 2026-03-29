package org.example.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class RedisRateLimiterConfig {

    /**
     * Used by the {@code RequestRateLimiter} filter: one counter per client IP.
     */
    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> {
            var remote = exchange.getRequest().getRemoteAddress();
            if (remote != null && remote.getAddress() != null) {
                return Mono.just(remote.getAddress().getHostAddress());
            }
            return Mono.just("unknown");
        };
    }
}

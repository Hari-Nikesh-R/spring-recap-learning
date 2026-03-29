package org.example.gateway.filter;

import java.util.UUID;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Post filter: runs after the downstream call completes. Typical uses: add response headers,
 * metrics, response body transforms (advanced).
 */
@Component
public class PostResponseProcessingFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            var response = exchange.getResponse();
            response.getHeaders().add("X-Gateway-Trace-Id", UUID.randomUUID().toString());
            response.getHeaders().add(HttpHeaders.CACHE_CONTROL, "no-store");
        }));
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 10;
    }
}

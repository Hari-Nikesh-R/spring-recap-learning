package org.example.gateway.filter;

import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Route-scoped filter: validates an API key header before proxying. Demonstrates cross-cutting
 * access control without embedding it in every microservice.
 */
@Component
public class ApiKeyGatewayFilterFactory extends AbstractGatewayFilterFactory<ApiKeyGatewayFilterFactory.Config> {

    public ApiKeyGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return List.of("headerName", "validKey");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String provided = exchange.getRequest().getHeaders().getFirst(config.getHeaderName());
            if (config.getValidKey() != null && config.getValidKey().equals(provided)) {
                return chain.filter(exchange);
            }
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        };
    }

    public static class Config {

        private String headerName = "X-API-Key";
        private String validKey;

        public String getHeaderName() {
            return headerName;
        }

        public void setHeaderName(String headerName) {
            this.headerName = headerName;
        }

        public String getValidKey() {
            return validKey;
        }

        public void setValidKey(String validKey) {
            this.validKey = validKey;
        }
    }
}

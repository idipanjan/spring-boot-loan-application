package com.project.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/loans/**")
                        .uri("lb://LOAN-MICROSERVICE")
                )
                .route(r -> r.path("/api/customers/**")
                        .uri("lb://CUSTOMER-MICROSERVICE")
                )
                .route(r -> r.path("/api/banks/**")
                        .uri("lb://BANK-MICROSERVICE")
                )
                .build();
    }
}

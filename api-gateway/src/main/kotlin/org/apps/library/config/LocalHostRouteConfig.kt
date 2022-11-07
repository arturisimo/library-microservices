package org.apps.library.config

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LocalHostRouteConfig() {

    @Bean
    fun localHostRoutes(builder: RouteLocatorBuilder): RouteLocator? {
        return builder.routes()
            .route { r ->
                r.path("/api/v1/users/**")
                    .filters { f -> f.rewritePath("/api/v1/users/(?<segment>.*)", "/api/v1/users/\${segment}") }
                    .uri("http://localhost:8081")
            }
            .route { r ->
                r.path("/api/v1/books/**")
                    .filters { f ->
                        f
                            .rewritePath("/api/v1/books/(?<segment>.*)", "/api/v1/books/\${segment}")
                    }
                    .uri("http://localhost:8082")
            }
            .route { r ->
                r.path("/api/v1/loans/**")
                    .filters { f ->
                        f
                            .rewritePath("/api/v1/loans/(?<segment>.*)", "/api/v1/loans/\${segment}")
                    }
                    .uri("http://localhost:8083")
            }
            .build()
    }


}

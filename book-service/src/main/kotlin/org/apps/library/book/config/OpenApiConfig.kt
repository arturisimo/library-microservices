package org.apps.library.book.config

import io.swagger.v3.oas.models.OpenAPI
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import io.swagger.v3.oas.models.servers.Server
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Value("\${api.version}")
    val version: String = "v1"

    @Value("\${server.port}")
    val port: String = "8082"

    @Bean
    fun customOpenAPI(): OpenAPI? {
        return OpenAPI()
            .servers(
                listOf(
                    Server().url("http://localhost:$port").description("Local"),
                )
            )
            .info(
                Info().title("Book API")
                    .description("Microservice Book API.")
                    .version(version)
            )
    }
}


package com.garveshtiwari.spiritual_app_backend.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI spiritualAppOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spiritual App API")
                        .version("v1")
                        .description("Backend APIs for the Spiritual App"));
    }
}

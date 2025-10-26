package br.com.greeks.greeks.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI greeksOpenApi() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("GREEKS API").version("v1").description("API Backend para o CP6 da disciplina DevOps - ADS FIAP"));
    }
}


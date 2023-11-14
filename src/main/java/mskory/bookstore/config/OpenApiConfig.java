package mskory.bookstore.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class OpenApiConfig {
    private static final String SECURITY_SCHEMA_NAME = "Bearer Authentication";
    @Value("${project.version}")
    private String projectVersion;
    @Value("${project.name}")
    private String projectName;

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(SECURITY_SCHEMA_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEMA_NAME, createApiKeyScheme()))
                .info(new Info().title(projectName).version(projectVersion));
    }

    @Bean
    public SecurityScheme createApiKeyScheme() {
        return new SecurityScheme()
                .name(SECURITY_SCHEMA_NAME)
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
}

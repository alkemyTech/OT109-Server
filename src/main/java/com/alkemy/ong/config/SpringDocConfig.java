package com.alkemy.ong.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI myOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI().info(new Info().title("Ong API")
                .description("DATOS DE PRUEBA:\n\n" +
                        "Usuarios administradores:\n" +
                        "* USERNAME: monicasala@gmail.com -------- PASSWORD: monicasala\n" +
                        "* USERNAME: tamaraceballos@gmail.com --- PASSWORD: tamaraceballos\n\n" +
                        "Usuarios regulares:\n" +
                        "* USERNAME: juanperez@gmail.com ----------- PASSWORD: juanperez\n" +
                        "* USERNAME: marcossanchez@gmail.com --- PASSWORD: marcossanchez")
                .version("v1"))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components().addSecuritySchemes(securitySchemeName,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));

    }
}

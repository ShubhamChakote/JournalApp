package com.smc.journalApp.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customConfig(){
        return new OpenAPI()
                .info(
                        new Info()
                        .title("Journal Application APIs")
                        .description("By Shubham Chakote"))
                .servers(
                        List.of(
                                new Server().url("https://springboot-journalapp-b0180e82173b.herokuapp.com/").description("live server"),
                                new Server().url("http://localhost:8081/").description("local server")
                        )
                )

                .tags(List.of(new Tag().name("Public APIs")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes(
                        "bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")
                ));

    }

}

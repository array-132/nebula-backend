package com.nebula.blogapp.config;

import java.util.Arrays;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
// import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
    
    public static final String AUTHORIZATION_HEADER = "Authorization";

    // private ApiKey apiKeys(){

    // }


    @Bean
    public GroupedOpenApi publicApi(){
        return GroupedOpenApi.builder()
               .group("default")
               .pathsToMatch("/**")
               .build();
    }

    @Bean
    public OpenAPI blogappOpenAPI(){
        return new OpenAPI()
                    .addSecurityItem(new SecurityRequirement().addList("bearer-jwt" ,Arrays.asList("global","accessEverything")))
                    .components(new Components().addSecuritySchemes("bearer-jwt",
                    new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                    .in(SecurityScheme.In.HEADER).name(AUTHORIZATION_HEADER)))
                    .info(new Info()
                    .title("Nebula Blogging API")
                    .description("A simple blogging application for learning purposes")
                    .version("v1.0.0")
                    .termsOfService("Terms of Service")
                    .contact(new Contact().name("Anurag").url("https://unknown").email("nebula.helpdesk@gmail.com"))
                    .license(new License().name("Apache 3.0").url("http://springdoc.org")))
                    ;
                //    .externalDocs(new ExternalDocumentation()
                //    .description("Nebula Blogapp Wiki Documentation")
                //    .url("https://nebula-app.wiki.github.org/docs"));
    }
}
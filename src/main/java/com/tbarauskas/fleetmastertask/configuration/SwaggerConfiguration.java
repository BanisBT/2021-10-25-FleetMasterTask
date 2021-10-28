package com.tbarauskas.fleetmastertask.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("Fleet Master task",
                "Fleet Master task",
                "REST API",
                "Term of service",
                new Contact("Tomas Barauskas", "https://github.com/BanisBT/2021-10-25-FleetMasterTask",
                        "banisbt@gmail.com"),
                "License of API", "API license URL", Collections.emptyList());
    }
}

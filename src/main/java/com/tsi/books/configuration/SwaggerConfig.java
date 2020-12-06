package com.tsi.books.configuration;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()

        .apis(RequestHandlerSelectors.basePackage("com.tsi.books"))
        //.paths(PathSelectors.regex("/api/.*"))
        .build()
        .pathMapping("/")
        .genericModelSubstitutes(ResponseEntity.class)
        .useDefaultResponseMessages(false)
        .apiInfo(apiInfo());

  }

  private ApiInfo apiInfo() {
    return new ApiInfo(
        "My REST API",
        "Some custom description of API.",
        "API TOS",
        "Terms of service",
        new Contact("Juan Carlos y Miguel Angel", "https://github.com/JuanCBM/Books",
            "myeaddress@company.com"),
        "License of API", "API license URL", Collections.emptyList());
  }
}

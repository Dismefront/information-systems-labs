package org.dismefront.app;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Information Systems Lab API")
                .version("1.0")
                .description(
                    "API for managing information system objects including products, organizations, persons, and related entities.")
                .contact(new Contact().name("Development Team").email("dev@example.com"))
                .license(new License().name("Apache 2.0").url("http://springdoc.org")))
        .servers(
            List.of(
                new Server().url("http://localhost:8080").description("Local development server"),
                new Server().url("https://api.example.com").description("Production server")));
  }
}

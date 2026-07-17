package dev.patika.veterinary;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    OpenAPI veterinaryOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Veterinary Management API")
                        .description("Clinic operations, appointment scheduling, and vaccination tracking.")
                        .version("0.0.1-SNAPSHOT")
                        .license(new License()
                                .name("MIT")
                                .url("https://github.com/okturan/Veterinary-Management-System-API/blob/master/LICENSE")))
                .externalDocs(new ExternalDocumentation()
                        .description("Source, setup, and verification")
                        .url("https://github.com/okturan/Veterinary-Management-System-API"));
    }
}

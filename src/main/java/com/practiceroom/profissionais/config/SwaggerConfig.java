package com.practiceroom.profissionais.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

/**
 * @author Willian Robert Scabora
 */
@Configuration
public class SwaggerConfig  {

    @Bean
    public OpenAPI myOpenAPI() {

        Info info = new Info()
                .title("Profissionais")
                .version("1.0")
                .description("Gerenciamento de Profissionais.");
        return new OpenAPI().info(info);
    }

}

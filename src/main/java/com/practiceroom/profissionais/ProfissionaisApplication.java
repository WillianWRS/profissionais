package com.practiceroom.profissionais;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@ComponentScan(basePackages = {"com.practiceroom.**"})
@OpenAPIDefinition(info = @Info(title = "Profissionais", version = "1", description = "Gerenciamento de Profissionais."))
public class ProfissionaisApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProfissionaisApplication.class, args);
	}

}

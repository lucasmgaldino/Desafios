package com.creditas.desafiobackendcreditas;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentationAPIConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder().group("LoanAnalyzer")
                .addOpenApiCustomiser(openApi -> openApi.info(new Info()
                        .title("Creditas's Challenge API")
                        .contact(new Contact()
                                .name("Lucas Almeida")
                                .email("lucasmgaldino@gmail.com"))
                        .version("0.0.1"))
                )
                .packagesToScan("com.creditas.desafiobackendcreditas")
                .build();
    }

}

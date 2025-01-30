package study.guilhermerodrigues17.study_libraryapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Library API",
                version = "v1",
                contact = @Contact(
                        name = "Guilherme Rodrigues Machado",
                        email = "guilhermerodriguesm23@gmail.com",
                        url = "libraryapi.com"
                )
        )
)
public class OpenApiConfiguration {
}

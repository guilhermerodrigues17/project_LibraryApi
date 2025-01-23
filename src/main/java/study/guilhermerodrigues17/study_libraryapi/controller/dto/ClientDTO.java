package study.guilhermerodrigues17.study_libraryapi.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record ClientDTO(
        @NotBlank(message = "Required field")
        String clientId,

        @NotBlank(message = "Required field")
        String clientSecret,

        @NotBlank(message = "Required field")
        String redirectUri,

        String scope
) {
}

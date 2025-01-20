package study.guilhermerodrigues17.study_libraryapi.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UserDTO(
        @NotBlank(message = "Required field")
        String username,

        @Email(message = "Invalid email")
        @NotBlank(message = "Required field")
        String email,

        @NotBlank(message = "Required field")
        String password,

        List<String> roles
) {
}

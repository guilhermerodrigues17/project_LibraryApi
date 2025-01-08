package study.guilhermerodrigues17.study_libraryapi.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import study.guilhermerodrigues17.study_libraryapi.model.Author;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorDTO(
        UUID id,
        @NotBlank(message = "Required field")
        @Size(min = 2, max = 100, message = "Invalid field size")
        String name,
        @NotNull(message = "Required field")
        @Past(message = "Invalid date. It cant be in the future")
        LocalDate birthDate,
        @NotBlank(message = "Required field")
        @Size(min = 2, max = 50, message = "Invalid field size")
        String nationality
) {

}

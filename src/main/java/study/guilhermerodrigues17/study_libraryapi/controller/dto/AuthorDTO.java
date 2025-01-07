package study.guilhermerodrigues17.study_libraryapi.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import study.guilhermerodrigues17.study_libraryapi.model.Author;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorDTO(
        UUID id,
        @NotBlank(message = "Required field")
        String name,
        @NotNull(message = "Required field")
        LocalDate birthDate,
        @NotBlank(message = "Required field")
        String nationality
) {

    public Author mapToAuthor() {
        Author author = new Author();
        author.setName(this.name);
        author.setBirthDate(this.birthDate);
        author.setNationality(this.nationality);

        return author;
    }
}

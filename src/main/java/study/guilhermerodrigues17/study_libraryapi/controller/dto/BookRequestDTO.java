package study.guilhermerodrigues17.study_libraryapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;
import study.guilhermerodrigues17.study_libraryapi.model.BookGenres;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Book Request")
public record BookRequestDTO(
        @ISBN
        @NotBlank(message = "Required Field")
        String isbn,
        @NotBlank(message = "Required Field")
        String title,
        @NotNull(message = "Required Field")
        @Past(message = "Invalid date. It cant be in the future")
        LocalDate publicationDate,
        BookGenres genre,
        BigDecimal price,
        @NotNull(message = "Required Field")
        UUID authorId
) {

}

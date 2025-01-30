package study.guilhermerodrigues17.study_libraryapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import study.guilhermerodrigues17.study_libraryapi.model.BookGenres;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Book Response")
public record BookResponseDTO(
        UUID id,
        String isbn,
        String title,
        LocalDate publicationDate,
        BookGenres genre,
        BigDecimal price,
        AuthorDTO author
) {

}

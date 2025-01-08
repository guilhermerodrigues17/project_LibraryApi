package study.guilhermerodrigues17.study_libraryapi.controller.dto;

import study.guilhermerodrigues17.study_libraryapi.model.BookGenres;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record BookRequestDTO(
        String isbn,
        String title,
        LocalDate publicationDate,
        BookGenres genre,
        BigDecimal price,
        UUID authorId
) {

}

package study.guilhermerodrigues17.study_libraryapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.guilhermerodrigues17.study_libraryapi.controller.dto.BookRequestDTO;
import study.guilhermerodrigues17.study_libraryapi.controller.dto.BookResponseDTO;
import study.guilhermerodrigues17.study_libraryapi.controller.mappers.BookMapper;
import study.guilhermerodrigues17.study_libraryapi.model.Book;
import study.guilhermerodrigues17.study_libraryapi.model.BookGenres;
import study.guilhermerodrigues17.study_libraryapi.service.BookService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
public class BookController implements GenericController {

    private final BookService service;
    private final BookMapper mapper;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid BookRequestDTO dto) {
        Book bookEntity = mapper.toEntity(dto);
        service.save(bookEntity);
        URI uri = generateHeaderLocation(bookEntity.getId());
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<BookResponseDTO> findById(@PathVariable String id) {
        return service.findById(UUID.fromString(id)).map(book -> {
            var dto = mapper.toDTO(book);
            return ResponseEntity.ok(dto);
        }).orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> searchBooksBySpecs(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "title", required = false)
            String title,
            @RequestParam(value = "author-name", required = false)
            String authorName,
            @RequestParam(value = "genre", required = false)
            BookGenres genre,
            @RequestParam(value = "publication-year", required = false)
            Integer publicationYear
    ) {
        List<Book> booksFound = service.searchBooksBySpecs(isbn, title, authorName, genre, publicationYear);
        List<BookResponseDTO> dtoList = booksFound.stream().map(mapper::toDTO).toList();

        return ResponseEntity.ok(dtoList);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteById(@PathVariable String id) {
        return service.findById(UUID.fromString(id)).map(book -> {
            service.deleteById(book);
            return ResponseEntity.noContent().build();
        }).orElseGet( () -> ResponseEntity.notFound().build() );
    }
}

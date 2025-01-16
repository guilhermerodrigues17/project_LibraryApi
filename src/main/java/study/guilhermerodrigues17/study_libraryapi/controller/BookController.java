package study.guilhermerodrigues17.study_libraryapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import study.guilhermerodrigues17.study_libraryapi.controller.dto.BookRequestDTO;
import study.guilhermerodrigues17.study_libraryapi.controller.dto.BookResponseDTO;
import study.guilhermerodrigues17.study_libraryapi.controller.mappers.BookMapper;
import study.guilhermerodrigues17.study_libraryapi.model.Book;
import study.guilhermerodrigues17.study_libraryapi.model.BookGenres;
import study.guilhermerodrigues17.study_libraryapi.service.BookService;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('MANAGER', 'OPERATOR')")
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
    public ResponseEntity<Page<BookResponseDTO>> searchBooksBySpecs(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "title", required = false)
            String title,
            @RequestParam(value = "author-name", required = false)
            String authorName,
            @RequestParam(value = "genre", required = false)
            BookGenres genre,
            @RequestParam(value = "publication-year", required = false)
            Integer publicationYear,
            @RequestParam(value = "page-number", defaultValue = "0")
            Integer pageNumber,
            @RequestParam(value = "page-size", defaultValue = "10")
            Integer pageSize
    ) {
        Page<Book> booksFound = service.searchBooksBySpecs(isbn, title, authorName, genre, publicationYear, pageNumber, pageSize);
        Page<BookResponseDTO> dtoList = booksFound.map(mapper::toDTO);

        return ResponseEntity.ok(dtoList);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteById(@PathVariable String id) {
        return service.findById(UUID.fromString(id)).map(book -> {
            service.deleteById(book);
            return ResponseEntity.noContent().build();
        }).orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateById(@PathVariable String id, @RequestBody @Valid BookRequestDTO dto) {
        return service.findById(UUID.fromString(id)).map(book -> {
            Book entity = mapper.toEntity(dto);
            book.setIsbn(entity.getIsbn());
            book.setTitle(entity.getTitle());
            book.setPublicationDate(entity.getPublicationDate());
            book.setGenre(entity.getGenre());
            book.setPrice(entity.getPrice());
            book.setAuthor(entity.getAuthor());

            service.updateById(book);
            return ResponseEntity.noContent().build();
        }).orElseGet( () -> ResponseEntity.notFound().build() );
    }
}

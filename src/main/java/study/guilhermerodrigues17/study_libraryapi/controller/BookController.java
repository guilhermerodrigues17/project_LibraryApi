package study.guilhermerodrigues17.study_libraryapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.guilhermerodrigues17.study_libraryapi.controller.dto.BookRequestDTO;
import study.guilhermerodrigues17.study_libraryapi.controller.mappers.BookMapper;
import study.guilhermerodrigues17.study_libraryapi.model.Book;
import study.guilhermerodrigues17.study_libraryapi.service.BookService;

import java.net.URI;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
public class BookController implements GenericController {

    private final BookService service;
    private final BookMapper mapper;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid BookRequestDTO dto) {
        Book bookEntity = mapper.toEntity(dto);
        service.save(bookEntity);
        URI uri = generateHeaderLocation(bookEntity.getId());
        return ResponseEntity.created(uri).build();
    }
}

package study.guilhermerodrigues17.study_libraryapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import study.guilhermerodrigues17.study_libraryapi.controller.dto.AuthorDTO;
import study.guilhermerodrigues17.study_libraryapi.model.Author;
import study.guilhermerodrigues17.study_libraryapi.service.AuthorService;

import java.net.URI;

@RestController
@RequestMapping("authors") //http://localhost:8080/authors
public class AuthorController {

    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody AuthorDTO author) {
        Author authorEntity = author.mapToAuthor();
        service.save(authorEntity);

        //http://localhost:8080/authors/uuid
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(authorEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}

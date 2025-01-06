package study.guilhermerodrigues17.study_libraryapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import study.guilhermerodrigues17.study_libraryapi.controller.dto.AuthorDTO;
import study.guilhermerodrigues17.study_libraryapi.model.Author;
import study.guilhermerodrigues17.study_libraryapi.service.AuthorService;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @GetMapping("{id}")
    public ResponseEntity<AuthorDTO> findById(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Author> optionalAuthor = service.findById(uuid);
        if (optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            AuthorDTO dto = new AuthorDTO(
                    author.getId(),
                    author.getName(),
                    author.getBirthDate(),
                    author.getNationality()
            );

            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> searchAuthors(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "nationality", required = false) String nationality
    ) {
        List<Author> resultList = service.searchAuthor(name, nationality);
        List<AuthorDTO> dtoList = resultList.stream().map(
                author -> new AuthorDTO(
                        author.getId(),
                        author.getName(),
                        author.getBirthDate(),
                        author.getNationality()
                )
        ).toList();

        return ResponseEntity.ok(dtoList);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateById(@PathVariable String id, @RequestBody AuthorDTO authorDto) {
        UUID uuid = UUID.fromString(id);
        Optional<Author> optionalAuthor = service.findById(uuid);
        if (optionalAuthor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Author author = optionalAuthor.get();
        author.setName(authorDto.name());
        author.setBirthDate(authorDto.birthDate());
        author.setNationality(authorDto.nationality());

        service.updateById(author);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Author> optionalAuthor = service.findById(uuid);
        if (optionalAuthor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.deleteAuthor(optionalAuthor.get());
        return ResponseEntity.noContent().build();
    }
}

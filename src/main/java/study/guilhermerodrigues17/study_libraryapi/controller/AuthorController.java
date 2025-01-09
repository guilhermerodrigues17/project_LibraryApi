package study.guilhermerodrigues17.study_libraryapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.guilhermerodrigues17.study_libraryapi.controller.dto.AuthorDTO;
import study.guilhermerodrigues17.study_libraryapi.controller.mappers.AuthorMapper;
import study.guilhermerodrigues17.study_libraryapi.model.Author;
import study.guilhermerodrigues17.study_libraryapi.service.AuthorService;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("authors") //http://localhost:8080/authors
@RequiredArgsConstructor
public class AuthorController implements GenericController {

    private final AuthorService service;
    private final AuthorMapper mapper;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid AuthorDTO dto) {
        Author authorEntity = mapper.toEntity(dto);
        service.save(authorEntity);

        //http://localhost:8080/authors/uuid
        URI location = generateHeaderLocation(authorEntity.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AuthorDTO> findById(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        return service.findById(uuid).map(author -> {
            AuthorDTO dto = mapper.toDTO(author);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> searchAuthors(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "nationality", required = false) String nationality
    ) {
        List<Author> resultList = service.searchAuthorByExample(name, nationality);
        List<AuthorDTO> dtoList = resultList
                .stream()
                .map(mapper::toDTO)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateById(@PathVariable String id, @RequestBody @Valid AuthorDTO authorDto) {
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

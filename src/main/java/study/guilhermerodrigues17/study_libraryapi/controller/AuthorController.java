package study.guilhermerodrigues17.study_libraryapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Tag(name = "Authors")
@Slf4j
public class AuthorController implements GenericController {

    private final AuthorService service;
    private final AuthorMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Save", description = "Save a new author.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Saved successful."),
            @ApiResponse(responseCode = "422", description = "Validation error."),
            @ApiResponse(responseCode = "409", description = "This author already exists.")
    })
    public ResponseEntity<Void> save(@RequestBody @Valid AuthorDTO dto) {
        log.info("New author registered: {}", dto.name());
        Author authorEntity = mapper.toEntity(dto);
        service.save(authorEntity);

        //http://localhost:8080/authors/uuid
        URI location = generateHeaderLocation(authorEntity.getId());
        return ResponseEntity.created(location).build();
    }


    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERATOR', 'MANAGER')")
    @Operation(summary = "Find By ID", description = "Return author data found by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author found."),
            @ApiResponse(responseCode = "404", description = "Author not found."),
    })
    public ResponseEntity<AuthorDTO> findById(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        return service.findById(uuid).map(author -> {
            AuthorDTO dto = mapper.toDTO(author);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERATOR', 'MANAGER')")
    @Operation(summary = "Search Authors", description = "Search author by query params.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success."),
    })
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
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Update By ID", description = "Update an existent author.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Updated successful."),
            @ApiResponse(responseCode = "404", description = "Author not found."),
            @ApiResponse(responseCode = "409", description = "This author already exists.")
    })
    public ResponseEntity<Void> updateById(@PathVariable String id, @RequestBody @Valid AuthorDTO authorDto) {
        log.info("Author ID updated: {}", id);
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
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Delete Author", description = "Delete an existent author.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deleted successful."),
            @ApiResponse(responseCode = "404", description = "Author not found."),
            @ApiResponse(responseCode = "400", description = "This author has registered books.")
    })
    public ResponseEntity<Void> deleteAuthor(@PathVariable String id) {
        log.info("Deleting author by ID: {}", id);
        UUID uuid = UUID.fromString(id);
        Optional<Author> optionalAuthor = service.findById(uuid);
        if (optionalAuthor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.deleteAuthor(optionalAuthor.get());
        return ResponseEntity.noContent().build();
    }
}

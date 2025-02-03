package study.guilhermerodrigues17.study_libraryapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import study.guilhermerodrigues17.study_libraryapi.controller.dto.ClientDTO;
import study.guilhermerodrigues17.study_libraryapi.controller.mappers.ClientMapper;
import study.guilhermerodrigues17.study_libraryapi.model.Client;
import study.guilhermerodrigues17.study_libraryapi.service.ClientService;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
@Tag(name = "Clients")
@Slf4j
public class ClientController {

    private final ClientService service;
    private final ClientMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Save", description = "Save a new client.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Saved successful."),
            @ApiResponse(responseCode = "422", description = "Validation error.")
    })
    public void save(@RequestBody @Valid ClientDTO dto) {
        log.info("Registering a new client {}, with scope {}.", dto.clientId(), dto.scope());
        Client clientEntity = mapper.toEntity(dto);
        service.save(clientEntity);
    }

    @GetMapping("{clientId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'OPERATOR')")
    @Operation(summary = "Find By Client ID", description = "Find client by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client found."),
            @ApiResponse(responseCode = "404", description = "Client not found.")
    })
    public ResponseEntity<ClientDTO> findByClientId(@PathVariable String clientId) {
        Client clientFound = service.findByClientId(clientId);
        ClientDTO dto = mapper.toDTO(clientFound);

        return ResponseEntity.ok(dto);
    }
}

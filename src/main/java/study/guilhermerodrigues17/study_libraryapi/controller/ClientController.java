package study.guilhermerodrigues17.study_libraryapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
public class ClientController {

    private final ClientService service;
    private final ClientMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('MANAGER')")
    public void save(@RequestBody @Valid ClientDTO dto) {
        Client clientEntity = mapper.toEntity(dto);
        service.save(clientEntity);
    }

    @GetMapping("{clientId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'OPERATOR')")
    public ResponseEntity<ClientDTO> findByClientId(@PathVariable String clientId) {
        Client clientFound = service.findByClientId(clientId);
        ClientDTO dto = mapper.toDTO(clientFound);

        return ResponseEntity.ok(dto);
    }
}

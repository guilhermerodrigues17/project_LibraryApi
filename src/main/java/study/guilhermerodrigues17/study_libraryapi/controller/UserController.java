package study.guilhermerodrigues17.study_libraryapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import study.guilhermerodrigues17.study_libraryapi.controller.dto.UserDTO;
import study.guilhermerodrigues17.study_libraryapi.controller.mappers.UserMapper;
import study.guilhermerodrigues17.study_libraryapi.service.UserService;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@Tag(name = "Users")
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Save", description = "Save a new user.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Saved successful."),
            @ApiResponse(responseCode = "422", description = "Validation error.")
    })
    public void save(@RequestBody @Valid UserDTO dto) {
        var user = mapper.toEntity(dto);
        service.save(user);
    }
}

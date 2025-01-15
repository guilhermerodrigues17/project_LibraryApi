package study.guilhermerodrigues17.study_libraryapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import study.guilhermerodrigues17.study_libraryapi.controller.dto.UserDTO;
import study.guilhermerodrigues17.study_libraryapi.controller.mappers.UserMapper;
import study.guilhermerodrigues17.study_libraryapi.service.UserService;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody UserDTO dto) {
        var user = mapper.toEntity(dto);
        service.save(user);
    }
}

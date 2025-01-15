package study.guilhermerodrigues17.study_libraryapi.controller.dto;

import java.util.List;

public record UserDTO(String username, String password, List<String> roles) {
}

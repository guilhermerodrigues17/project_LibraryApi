package study.guilhermerodrigues17.study_libraryapi.controller.mappers;

import org.mapstruct.Mapper;
import study.guilhermerodrigues17.study_libraryapi.controller.dto.UserDTO;
import study.guilhermerodrigues17.study_libraryapi.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDTO dto);
}

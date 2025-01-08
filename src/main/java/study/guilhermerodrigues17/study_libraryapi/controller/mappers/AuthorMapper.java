package study.guilhermerodrigues17.study_libraryapi.controller.mappers;

import org.mapstruct.Mapper;
import study.guilhermerodrigues17.study_libraryapi.controller.dto.AuthorDTO;
import study.guilhermerodrigues17.study_libraryapi.model.Author;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    Author toEntity(AuthorDTO dto);

    AuthorDTO toDTO(Author author);
}

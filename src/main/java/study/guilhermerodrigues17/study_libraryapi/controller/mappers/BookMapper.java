package study.guilhermerodrigues17.study_libraryapi.controller.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import study.guilhermerodrigues17.study_libraryapi.controller.dto.BookRequestDTO;
import study.guilhermerodrigues17.study_libraryapi.controller.dto.BookResponseDTO;
import study.guilhermerodrigues17.study_libraryapi.model.Book;
import study.guilhermerodrigues17.study_libraryapi.repository.AuthorRepository;

@Mapper(componentModel = "spring", uses = AuthorMapper.class)
public abstract class BookMapper {

    @Autowired
    AuthorRepository authorRepository;

    @Mapping(target = "author", expression = "java( authorRepository.findById(dto.authorId()).orElse(null) )")
    public abstract Book toEntity(BookRequestDTO dto);

    public abstract BookResponseDTO toDTO(Book book);
}

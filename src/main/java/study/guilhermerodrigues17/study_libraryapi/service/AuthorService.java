package study.guilhermerodrigues17.study_libraryapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import study.guilhermerodrigues17.study_libraryapi.exceptions.NotAllowedOperationException;
import study.guilhermerodrigues17.study_libraryapi.model.Author;
import study.guilhermerodrigues17.study_libraryapi.repository.AuthorRepository;
import study.guilhermerodrigues17.study_libraryapi.repository.BookRepository;
import study.guilhermerodrigues17.study_libraryapi.security.SecurityService;
import study.guilhermerodrigues17.study_libraryapi.validator.AuthorValidator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository repository;
    private final AuthorValidator validator;
    private final BookRepository bookRepository;
    private final SecurityService securityService;

    public void save(Author author) {
        validator.validate(author);
        author.setUser(securityService.getUserLogged());
        repository.save(author);
    }

    public void updateById(Author author) {
        if (author.getId() == null) {
            throw new IllegalArgumentException("The author must exist in the database!");
        }

        validator.validate(author);
        repository.save(author);
    }

    public Optional<Author> findById(UUID id) {
        return repository.findById(id);
    }

    public void deleteAuthor(Author author) {
        if (authorHaveBook(author)) {
            throw new NotAllowedOperationException("This author have registered books!");
        }
        repository.delete(author);
    }

    public List<Author> searchAuthorByExample(String name, String nationality) {
        var author = new Author();
        author.setName(name);
        author.setNationality(nationality);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Author> authorExample = Example.of(author, matcher);

        return repository.findAll(authorExample);
    }

    private boolean authorHaveBook(Author author) {
        return bookRepository.existsByAuthor(author);
    }
}

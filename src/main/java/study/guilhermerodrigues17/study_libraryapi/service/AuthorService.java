package study.guilhermerodrigues17.study_libraryapi.service;

import org.springframework.stereotype.Service;
import study.guilhermerodrigues17.study_libraryapi.model.Author;
import study.guilhermerodrigues17.study_libraryapi.repository.AuthorRepository;
import study.guilhermerodrigues17.study_libraryapi.validator.AuthorValidator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {

    private final AuthorRepository repository;
    private final AuthorValidator validator;

    public AuthorService(AuthorRepository repository, AuthorValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public void save(Author author) {
        validator.validate(author);
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
        repository.delete(author);
    }

    public List<Author> searchAuthor(String name, String nationality) {
        if (name != null && nationality != null) {
            return repository.findByNameAndNationality(name, nationality);
        }

        if (name != null) {
            return repository.findByName(name);
        }

        if (nationality != null) {
            return repository.findByNationality(nationality);
        }

        return repository.findAll();
    }

}

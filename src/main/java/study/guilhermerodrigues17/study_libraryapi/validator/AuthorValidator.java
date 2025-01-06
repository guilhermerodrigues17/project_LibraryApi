package study.guilhermerodrigues17.study_libraryapi.validator;

import org.springframework.stereotype.Component;
import study.guilhermerodrigues17.study_libraryapi.exceptions.DuplicatedRegisterException;
import study.guilhermerodrigues17.study_libraryapi.model.Author;
import study.guilhermerodrigues17.study_libraryapi.repository.AuthorRepository;

import java.util.Optional;

@Component
public class AuthorValidator {

    private final AuthorRepository repository;

    public AuthorValidator(AuthorRepository repository) {
        this.repository = repository;
    }

    public void validate(Author author) {
        if (authorAlreadyExists(author)) {
            throw new DuplicatedRegisterException("This author already exists!");
        }
    }

    private boolean authorAlreadyExists(Author author) {
        Optional<Author> authorFound = repository.findByNameAndBirthDateAndNationality(
                author.getName(),
                author.getBirthDate(),
                author.getNationality()
        );

        if (author.getId() == null) {
            return authorFound.isPresent();
        }

        return !author.getId().equals(authorFound.get().getId()) && authorFound.isPresent();
    }
}

package study.guilhermerodrigues17.study_libraryapi.service;

import org.springframework.stereotype.Service;
import study.guilhermerodrigues17.study_libraryapi.model.Author;
import study.guilhermerodrigues17.study_libraryapi.repository.AuthorRepository;

@Service
public class AuthorService {

    private final AuthorRepository repository;

    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    public Author save(Author author) {
        return repository.save(author);
    }
}

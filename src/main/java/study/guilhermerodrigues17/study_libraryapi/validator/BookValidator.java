package study.guilhermerodrigues17.study_libraryapi.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import study.guilhermerodrigues17.study_libraryapi.exceptions.DuplicatedRegisterException;
import study.guilhermerodrigues17.study_libraryapi.model.Book;
import study.guilhermerodrigues17.study_libraryapi.repository.BookRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookValidator {

    private final BookRepository repository;

    public void validate(Book book) {
        if (isbnAlreadyExists(book)) {
            throw new DuplicatedRegisterException("ISBN already exists!");
        }
    }

    public boolean isbnAlreadyExists(Book book) {
        Optional<Book> bookOptional = repository.findByIsbn(book.getIsbn());

        if (book.getId() == null) {
            return bookOptional.isPresent();
        }

        return bookOptional.map(Book::getId).stream().anyMatch(id -> !id.equals(book.getId()));
    }
}

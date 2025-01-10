package study.guilhermerodrigues17.study_libraryapi.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import study.guilhermerodrigues17.study_libraryapi.exceptions.DuplicatedRegisterException;
import study.guilhermerodrigues17.study_libraryapi.exceptions.InvalidFieldException;
import study.guilhermerodrigues17.study_libraryapi.model.Book;
import study.guilhermerodrigues17.study_libraryapi.repository.BookRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookValidator {

    private static final int PUBLICATION_YEAR = 2020;

    private final BookRepository repository;

    public void validate(Book book) {
        if (isbnAlreadyExists(book)) {
            throw new DuplicatedRegisterException("ISBN already exists!");
        }

        if (isMandatoryPriceNull(book)) {
            throw new InvalidFieldException("Price is mandatory for books released after 2020!", "Price");
        }
    }

    private boolean isMandatoryPriceNull(Book book) {
        return book.getPrice() == null && book.getPublicationDate().getYear() >= PUBLICATION_YEAR;
    }

    public boolean isbnAlreadyExists(Book book) {
        Optional<Book> bookOptional = repository.findByIsbn(book.getIsbn());

        if (book.getId() == null) {
            return bookOptional.isPresent();
        }

        return bookOptional.map(Book::getId).stream().anyMatch(id -> !id.equals(book.getId()));
    }
}

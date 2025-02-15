package study.guilhermerodrigues17.study_libraryapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import study.guilhermerodrigues17.study_libraryapi.model.Book;
import study.guilhermerodrigues17.study_libraryapi.model.BookGenres;
import study.guilhermerodrigues17.study_libraryapi.repository.BookRepository;
import study.guilhermerodrigues17.study_libraryapi.security.SecurityService;
import study.guilhermerodrigues17.study_libraryapi.validator.BookValidator;

import java.util.Optional;
import java.util.UUID;

import static study.guilhermerodrigues17.study_libraryapi.repository.specs.BookSpecs.*;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;
    private final BookValidator validator;
    private final SecurityService securityService;

    public Book save(Book book) {
        validator.validate(book);
        book.setUser(securityService.getUserLogged());
        return repository.save(book);
    }

    public Optional<Book> findById(UUID id) {
        return repository.findById(id);
    }

    public void deleteById(Book book) {
        repository.delete(book);
    }

    public Page<Book> searchBooksBySpecs(String isbn, String title, String authorName,
                                         BookGenres genre, Integer publicationYear, Integer pageNumber, Integer pageSize) {
        Specification<Book> specs = Specification.where(
                (root, query, criteriaBuilder) ->
                        criteriaBuilder.conjunction()
        );

        if (isbn != null) specs = specs.and(isbnEqual(isbn));
        if (title != null) specs = specs.and(titleLike(title));
        if (genre != null) specs = specs.and(genreEqual(genre));
        if (publicationYear != null) specs = specs.and(publicationYearEqual(publicationYear));
        if (authorName != null) specs = specs.and(authorNameLike(authorName));

        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);

        return repository.findAll(specs, pageRequest);
    }

    public void updateById(Book book) {
        if (book.getId() == null) {
            throw new IllegalArgumentException("The book must exist in the database!");
        }

        validator.validate(book);
        repository.save(book);
    }
}

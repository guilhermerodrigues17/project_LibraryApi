package study.guilhermerodrigues17.study_libraryapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import study.guilhermerodrigues17.study_libraryapi.model.Book;
import study.guilhermerodrigues17.study_libraryapi.model.BookGenres;
import study.guilhermerodrigues17.study_libraryapi.repository.BookRepository;

import static study.guilhermerodrigues17.study_libraryapi.repository.specs.BookSpecs.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;

    public Book save(Book book) {
        return repository.save(book);
    }

    public Optional<Book> findById(UUID id) {
        return repository.findById(id);
    }

    public void deleteById(Book book) {
        repository.delete(book);
    }

    public List<Book> searchBooksBySpecs(String isbn, String title, String authorName,
                                         BookGenres genre, Integer publicationYear) {
        Specification<Book> specs = Specification.where(
                (root, query, criteriaBuilder) ->
                        criteriaBuilder.conjunction()
        );

        if (isbn != null) specs = specs.and(isbnEqual(isbn));
        if (title != null) specs = specs.and(titleLike(title));
        if (genre != null) specs = specs.and(genreEqual(genre));
        if (publicationYear != null) specs = specs.and(publicationYearEqual(publicationYear));
        if (authorName != null) specs = specs.and(authorNameLike(authorName));

        return repository.findAll(specs);
    }

    public void updateById(Book book) {
        if (book.getId() == null) {
            throw new IllegalArgumentException("The book must exist in the database!");
        }

        repository.save(book);
    }
}

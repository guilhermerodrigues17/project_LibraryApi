package study.guilhermerodrigues17.study_libraryapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.guilhermerodrigues17.study_libraryapi.model.Book;
import study.guilhermerodrigues17.study_libraryapi.repository.BookRepository;

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
}

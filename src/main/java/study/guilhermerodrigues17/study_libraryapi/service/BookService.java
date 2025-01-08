package study.guilhermerodrigues17.study_libraryapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.guilhermerodrigues17.study_libraryapi.repository.BookRepository;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;
}

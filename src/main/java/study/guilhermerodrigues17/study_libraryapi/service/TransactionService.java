package study.guilhermerodrigues17.study_libraryapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.guilhermerodrigues17.study_libraryapi.model.Author;
import study.guilhermerodrigues17.study_libraryapi.model.Book;
import study.guilhermerodrigues17.study_libraryapi.model.BookGenres;
import study.guilhermerodrigues17.study_libraryapi.repository.AuthorRepository;
import study.guilhermerodrigues17.study_libraryapi.repository.BookRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class TransactionService {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;

    @Transactional
    public void executeTransaction() {
        //save author
        Author author = new Author();
        author.setName("Gerald");
        author.setNationality("British");
        author.setBirthDate(LocalDate.of(1980, 5, 28));

        authorRepository.save(author);

        //save book relation
        Book book = new Book();
        book.setIsbn("111-11-11111-11-1");
        book.setPrice(BigDecimal.valueOf(299.99));
        book.setGenre(BookGenres.FANTASIA);
        book.setTitle("The Fantasy");
        book.setPublicationDate(LocalDate.of(2024, 3, 25));
        book.setAuthor(author);

        bookRepository.save(book);

        /*
        Esse if lançaria uma exception que causaria o rollback na transação
            if (author.getName().equals("Gerald")) {
                throw new RuntimeException("Rollback!");
            }
         */
    }
}

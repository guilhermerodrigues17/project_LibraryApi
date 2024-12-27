package study.guilhermerodrigues17.study_libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.guilhermerodrigues17.study_libraryapi.model.Author;
import study.guilhermerodrigues17.study_libraryapi.model.Book;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    //select * from book where id_author = id
    List<Book> findByAuthor(Author author);

    //select * from book where title = ?
    List<Book> findByTitle(String title);

    //select * from book where isbn = ?
    List<Book> findByIsbn(String isbn);

    //select * from book where isbn = ? and price = ?
    Optional<Book> findByIsbnAndPrice(String isbn, BigDecimal price);

    //select * from book where isbn = ? or title = ?
    List<Book> findByIsbnOrTitle(String isbn, String title);
}

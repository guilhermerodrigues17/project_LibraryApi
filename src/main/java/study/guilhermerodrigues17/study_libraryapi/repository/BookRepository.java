package study.guilhermerodrigues17.study_libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import study.guilhermerodrigues17.study_libraryapi.model.Author;
import study.guilhermerodrigues17.study_libraryapi.model.Book;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    //select * from book where id_author = id
    List<Book> findByAuthor(Author author);

    //select * from book where title = ?
    List<Book> findByTitleOrderByTitle(String title);

    //select * from book where isbn = ?
    List<Book> findByIsbn(String isbn);

    //select * from book where isbn = ? and price = ?
    Optional<Book> findByIsbnAndPrice(String isbn, BigDecimal price);

    //select * from book where isbn = ? or title = ?
    List<Book> findByIsbnOrTitle(String isbn, String title);

    List<Book> findByPublicationDateBetween(LocalDate start, LocalDate end);

    @Query("select b from Book b order by b.title, b.price")
    List<Book> allBooksOrdered();

    @Query("select a from Book b join b.author a")
    List<Author> allBooksAuthors();

    @Query("select distinct b.title from Book b")
    List<String> distinctBookNamesList();

    @Query("""
            select b.genre
            from Book b
            join b.author a
            where a.nationality = 'Brazilian'
            order by b.genre
            """)
    List<String> genresByBrazilianAuthorsList();
}

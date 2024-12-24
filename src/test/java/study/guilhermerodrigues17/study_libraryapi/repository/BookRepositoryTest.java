package study.guilhermerodrigues17.study_libraryapi.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.guilhermerodrigues17.study_libraryapi.model.Author;
import study.guilhermerodrigues17.study_libraryapi.model.Book;
import study.guilhermerodrigues17.study_libraryapi.model.BookGenres;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    BookRepository repository;

    @Autowired
    AuthorRepository authorRepository;

    @Test
    public void saveBookTest() {
        Book book = new Book();
        book.setIsbn("111-11-11111-11-1");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGenre(BookGenres.FICCAO);
        book.setTitle("UFO");
        book.setPublicationDate(LocalDate.of(1980, 2, 25));

        Author author = authorRepository.findById(UUID.fromString("fb4fbe8b-c054-474d-8998-979f1a4ac6ef")).orElse(null);
        book.setAuthor(author);

        repository.save(book);
    }

    @Test
    public void saveWithCascadeTest() {
        Book book = new Book();
        book.setIsbn("111-11-11111-11-1");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGenre(BookGenres.ROMANCE);
        book.setTitle("Bridgerton");
        book.setPublicationDate(LocalDate.of(1980, 2, 25));

        Author author = new Author();
        author.setName("Maria");
        author.setBirthDate(LocalDate.of(1995, 6, 21));
        author.setNationality("Canadian");

        book.setAuthor(author);

        repository.save(book);
    }

}
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

        //Cascade ALL pode gerar problema na database; Não aconselhado em prod
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

    @Test
    public void saveAuthorAndBookTest() {

        //Opção mais utilizada em prod
        Book book = new Book();
        book.setIsbn("111-11-11111-11-1");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGenre(BookGenres.BIOGRAFIA);
        book.setTitle("Lula, volume 1: Biografia");
        book.setPublicationDate(LocalDate.of(2003, 1, 1));

        Author author = new Author();
        author.setName("Luiz Inácio Lula da Silva");
        author.setBirthDate(LocalDate.of(1945, 10, 27));
        author.setNationality("Brazilian");

        authorRepository.save(author);

        book.setAuthor(author);

        repository.save(book);
    }

    @Test
    void updateBookAuthorTest() {
        UUID id = UUID.fromString("35452446-926d-4f3d-8768-b09ea119a210");
        var bookToUpdate = repository.findById(id).orElse(null);

        var newAuthor = authorRepository.findById(UUID.fromString("4e3f9cf9-a6e9-45d2-ae89-ad9ad3d7266a")).orElse(null);
        bookToUpdate.setAuthor(newAuthor);

        repository.save(bookToUpdate);
    }
}
package study.guilhermerodrigues17.study_libraryapi.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.guilhermerodrigues17.study_libraryapi.model.Author;
import study.guilhermerodrigues17.study_libraryapi.model.Book;
import study.guilhermerodrigues17.study_libraryapi.model.BookGenres;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AuthorRepositoryTest {

    @Autowired
    AuthorRepository repository;

    @Autowired
    BookRepository bookRepository;

    @Test
    public void saveAuthorTest() {
        Author author = new Author();
        author.setName("José");
        author.setNationality("Brazilian");
        author.setBirthDate(LocalDate.of(1950, 1, 4));

        Author savedAuthor = repository.save(author);
        System.out.println("Saved author: " + savedAuthor);
    }

    @Test
    public void updateAuthorTest() {
        var id = UUID.fromString("aa0d1ec5-389f-48b9-9d28-de1f82219db7");

        Optional<Author> optionalAuthor = repository.findById(id);

        if (optionalAuthor.isPresent()) {
            Author authorFound = optionalAuthor.get();
            System.out.println("Author found: " + authorFound);

            authorFound.setBirthDate(LocalDate.of(1960, 1, 4));

            repository.save(authorFound);
        }
    }

    @Test
    public void findAllListTest() {
        List<Author> authorList = repository.findAll();
        authorList.forEach(System.out::println);
    }

    @Test
    public void countTest() {
        System.out.println("Repository count: " + repository.count());
    }

    @Test
    public void deleteByIdTest() {
        UUID id = UUID.fromString("5c157528-9dbf-477b-916c-d944ef40cb60");
        repository.deleteById(id);
    }

    @Test
    public void deleteByObj() {
        UUID id = UUID.fromString("2cceb5fb-61b3-4585-89ae-1a228ad60890");
        Author obj = repository.findById(id).get();
        repository.delete(obj);
    }

    @Test
    void saveAuthorAndBooksTest() {
        Author author = new Author();
        author.setName("Antonie");
        author.setNationality("French");
        author.setBirthDate(LocalDate.of(1970, 9, 12));

        Book book = new Book();
        book.setIsbn("111-11-11111-11-1");
        book.setPrice(BigDecimal.valueOf(299.99));
        book.setGenre(BookGenres.MISTERIO);
        book.setTitle("The Mystery");
        book.setPublicationDate(LocalDate.of(1999, 3, 25));
        book.setAuthor(author);

        Book book2 = new Book();
        book2.setIsbn("111-11-11111-11-1");
        book2.setPrice(BigDecimal.valueOf(299.99));
        book2.setGenre(BookGenres.MISTERIO);
        book2.setTitle("The Mystery 2");
        book2.setPublicationDate(LocalDate.of(2000, 6, 6));
        book2.setAuthor(author);

        author.setBooks(new ArrayList<>());
        author.getBooks().add(book);
        author.getBooks().add(book2);

        repository.save(author);
    }

    @Test
    void booksAuthorTest() {
        UUID id = UUID.fromString("1b2c86f5-fccc-434b-8e9f-9610cd510355");
        Author author = repository.findById(id).get();

        //Query books from author
        List<Book> booksList = bookRepository.findByAuthor(author);
        author.setBooks(booksList);

        author.getBooks().forEach(System.out::println);
    }
}

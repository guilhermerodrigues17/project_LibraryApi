package study.guilhermerodrigues17.study_libraryapi.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.guilhermerodrigues17.study_libraryapi.model.Author;
import study.guilhermerodrigues17.study_libraryapi.model.Book;
import study.guilhermerodrigues17.study_libraryapi.model.BookGenres;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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

    @Test
    void deleteById() {
        UUID id = UUID.fromString("35452446-926d-4f3d-8768-b09ea119a210");
        var bookToDelete = repository.findById(id).orElse(null);

        repository.deleteById(id);
    }

    @Test
    void deleteWithCascade() {

        //Necessário alterar o cascade type na classe Book; Tomar cuidado com a deleção em cascata
        UUID id = UUID.fromString("c2580f5c-bff9-42d0-9186-e28d0789145f");
        var bookToDelete = repository.findById(id).orElse(null);

        repository.deleteById(id);
    }

    @Test
    @Transactional
    void findByIdTest() {
        UUID id = UUID.fromString("b6916b27-bcab-4d02-82c7-c90de4e6d6ab");
        Book book = repository.findById(id).orElse(null);

        System.out.println("Book: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor().getName());
    }

    @Test
    void findByTitleTest() {
        List<Book> books = repository.findByTitleOrderByTitle("The Mystery");

        books.forEach(System.out::println);
    }

    @Test
    void findByIsbnTest() {
        Optional<Book> book = repository.findByIsbn("111-11-11111-11-1");

        book.ifPresent(System.out::println);
    }

    @Test
    void findByIsbnAndPriceTest() {
        String isbn = "111-11-11111-11-1";
        BigDecimal price = BigDecimal.valueOf(100.00);

        Optional<Book> bookOptional = repository.findByIsbnAndPrice(isbn, price);
        Book book = bookOptional.orElse(null);

        System.out.println(book);
    }

    @Test
    void findByIsbnOrTitleTest() {
        String isbn = "111-11-11111-11-1";
        String title = "The Mystery";
        List<Book> books = repository.findByIsbnOrTitle(isbn, title);

        books.forEach(System.out::println);
    }

    @Test
    void findByPublicationDateBetween() {
        LocalDate start = LocalDate.of(1999, 1, 1);
        LocalDate end = LocalDate.of(2000, 12, 31);
        List<Book> books = repository.findByPublicationDateBetween(start, end);

        books.forEach(System.out::println);
    }

    @Test
    void allBooksOrderedTest() {
        List<Book> books = repository.allBooksOrdered();

        books.forEach(System.out::println);
    }

    @Test
    void allBooksAuthorsTest() {
        List<Author> books = repository.allBooksAuthors();

        books.forEach(System.out::println);
    }

    @Test
    void distinctBookNamesListTest() {
        List<String> books = repository.distinctBookNamesList();

        books.forEach(System.out::println);
    }

    @Test
    void genresByBrazilianAuthorsListTest() {
        List<String> genres = repository.genresByBrazilianAuthorsList();

        genres.forEach(System.out::println);
    }

    @Test
    void findByGenreTest() {
        List<Book> genres = repository.findByGenre(BookGenres.MISTERIO, "publicationDate");

        genres.forEach(System.out::println);
    }

    @Test
    void findByGenrePositionalTest() {
        List<Book> genres = repository.findByGenre(BookGenres.MISTERIO, "publicationDate");

        genres.forEach(System.out::println);
    }

    @Test
    void deleteByGenreTest() {
        repository.deleteByGenre(BookGenres.CIENCIA);
    }

    @Test
    void updatePublicationDateTest() {
        UUID id = UUID.fromString("45fa43a3-6706-4356-88e7-ce8c458b0e88");
        repository.updatePublicationDate(id, LocalDate.of(2001, 1, 1));
    }
}
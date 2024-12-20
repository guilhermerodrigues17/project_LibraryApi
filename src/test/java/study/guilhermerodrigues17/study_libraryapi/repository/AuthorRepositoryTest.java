package study.guilhermerodrigues17.study_libraryapi.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.guilhermerodrigues17.study_libraryapi.model.Author;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AuthorRepositoryTest {

    @Autowired
    AuthorRepository repository;

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
}

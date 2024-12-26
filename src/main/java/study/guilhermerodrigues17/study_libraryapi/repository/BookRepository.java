package study.guilhermerodrigues17.study_libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.guilhermerodrigues17.study_libraryapi.model.Author;
import study.guilhermerodrigues17.study_libraryapi.model.Book;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    //select * from book where id_author = id
    List<Book> findByAuthor(Author author);
}

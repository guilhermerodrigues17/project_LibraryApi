package study.guilhermerodrigues17.study_libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.guilhermerodrigues17.study_libraryapi.model.Book;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
}

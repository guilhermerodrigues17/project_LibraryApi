package study.guilhermerodrigues17.study_libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.guilhermerodrigues17.study_libraryapi.model.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String username);

    User findByEmail(String email);
}

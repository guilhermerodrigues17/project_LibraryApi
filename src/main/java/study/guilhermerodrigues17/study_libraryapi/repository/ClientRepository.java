package study.guilhermerodrigues17.study_libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.guilhermerodrigues17.study_libraryapi.model.Client;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

    Client findByClientId(String clientId);
}

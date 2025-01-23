package study.guilhermerodrigues17.study_libraryapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import study.guilhermerodrigues17.study_libraryapi.model.Client;
import study.guilhermerodrigues17.study_libraryapi.repository.ClientRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final PasswordEncoder encoder;

    public void save(Client client) {
        String encodedSecret = encoder.encode(client.getClientSecret());
        client.setClientSecret(encodedSecret);
        repository.save(client);
    }

    public Client findByClientId(String clientId) {
        return repository.findByClientId(clientId);
    }
}

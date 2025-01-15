package study.guilhermerodrigues17.study_libraryapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import study.guilhermerodrigues17.study_libraryapi.model.User;
import study.guilhermerodrigues17.study_libraryapi.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public void save(User user){
        var password = user.getPassword();
        user.setPassword(encoder.encode(password));
        repository.save(user);
    }

    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }
}

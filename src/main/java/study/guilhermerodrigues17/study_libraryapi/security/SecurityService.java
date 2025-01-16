package study.guilhermerodrigues17.study_libraryapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import study.guilhermerodrigues17.study_libraryapi.model.User;
import study.guilhermerodrigues17.study_libraryapi.service.UserService;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UserService userService;

    public User getUserLogged() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        return userService.findByUsername(username);
    }
}

package study.guilhermerodrigues17.study_libraryapi.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import study.guilhermerodrigues17.study_libraryapi.security.CustomAuthentication;

@Controller
public class LoginViewController {

    @GetMapping("/login")
    public String findLoginView() {
        return "login";
    }

    @GetMapping("/")
    @ResponseBody
    public String findHomeView(Authentication authentication) {
        if (authentication instanceof CustomAuthentication customAuth) {
            System.out.println(customAuth.getUser());
        }
        return "Hello, " + authentication.getName();
    }

}

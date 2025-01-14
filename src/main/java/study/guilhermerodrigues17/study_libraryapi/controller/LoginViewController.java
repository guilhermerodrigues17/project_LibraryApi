package study.guilhermerodrigues17.study_libraryapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginViewController {

    @GetMapping("/login")
    public String findLoginView() {
        return "login";
    }
}

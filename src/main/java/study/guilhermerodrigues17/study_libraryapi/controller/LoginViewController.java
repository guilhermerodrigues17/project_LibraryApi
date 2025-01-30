package study.guilhermerodrigues17.study_libraryapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import study.guilhermerodrigues17.study_libraryapi.security.CustomAuthentication;

@Controller
@Tag(name = "Login")
public class LoginViewController {

    @GetMapping("/login")
    @Operation(summary = "Find Login View", description = "Return a login view.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success.")
    })
    public String findLoginView() {
        return "login";
    }

    @GetMapping("/")
    @ResponseBody
    @Operation(summary = "Find Home View", description = "Return a home view.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success.")
    })
    public String findHomeView(Authentication authentication) {
        if (authentication instanceof CustomAuthentication customAuth) {
            System.out.println(customAuth.getUser());
        }
        return "Hello, " + authentication.getName();
    }

    @GetMapping("/authorized")
    @ResponseBody
    @Operation(summary = "Get Code", description = "Get an authorization code.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public String getCode(@RequestParam String code) {
        return "Generated authentication code: " + code;
    }
}

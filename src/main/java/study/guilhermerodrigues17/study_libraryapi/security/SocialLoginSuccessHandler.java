package study.guilhermerodrigues17.study_libraryapi.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import study.guilhermerodrigues17.study_libraryapi.model.User;
import study.guilhermerodrigues17.study_libraryapi.service.UserService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SocialLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = token.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        User user = userService.findByEmail(email);
        authentication = new CustomAuthentication(user);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}

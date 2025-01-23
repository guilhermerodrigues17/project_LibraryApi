package study.guilhermerodrigues17.study_libraryapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import study.guilhermerodrigues17.study_libraryapi.security.SocialLoginSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, SocialLoginSuccessHandler successHandler) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(configurer -> configurer.loginPage("/login"))
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorization -> {
                    authorization.requestMatchers("/login").permitAll();
                    authorization.requestMatchers("/users/**").permitAll();

                    authorization.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> {
                    oauth2.loginPage("/login");
                    oauth2.successHandler(successHandler);
                })
                .oauth2ResourceServer(oAuth2RS -> oAuth2RS.jwt(Customizer.withDefaults()))
                .build();
    }

    //Configura prefixo ROLE do security
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    //Configura prefixo SCOPE do token JWT
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        var authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix("");

        var converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);

        return converter;
    }
}

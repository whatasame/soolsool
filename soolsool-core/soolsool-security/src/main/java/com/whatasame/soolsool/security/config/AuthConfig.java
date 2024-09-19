package com.whatasame.soolsool.security.config;

import com.whatasame.soolsool.security.handler.AccessDeniedHandlerImpl;
import com.whatasame.soolsool.security.handler.AuthenticationEntryPointImpl;
import com.whatasame.soolsool.security.jwt.JwtFilter;
import com.whatasame.soolsool.security.jwt.JwtParser;
import com.whatasame.soolsool.security.jwt.JwtValidator;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthConfig {

    private final AccessDeniedHandlerImpl accessDeniedHandler;
    private final AuthenticationEntryPointImpl authenticationEntryPoint;

    private final JwtValidator jwtValidator;
    private final JwtParser jwtParser;

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http, final JwtValidator jwtValidator)
            throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .addFilterBefore(new JwtFilter(jwtParser, jwtValidator), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(handling -> handling.accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR)
                .requestMatchers(HttpMethod.POST, "/members", "/auth/login");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

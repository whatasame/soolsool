package com.whatasame.soolsool.jwt.config;

import com.whatasame.soolsool.jwt.handler.AccessDeniedHandlerImpl;
import com.whatasame.soolsool.jwt.handler.AuthenticationEntryPointImpl;
import com.whatasame.soolsool.jwt.jwt.JwtFilter;
import com.whatasame.soolsool.jwt.jwt.JwtParser;
import com.whatasame.soolsool.jwt.jwt.JwtValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class JwtConfig {

    private final AccessDeniedHandlerImpl accessDeniedHandler;
    private final AuthenticationEntryPointImpl authenticationEntryPoint;

    private final JwtValidator jwtValidator;
    private final JwtParser jwtParser;

    @Bean
    public SecurityFilterChain jwtFilterChain(final HttpSecurity http, final JwtValidator jwtValidator)
            throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .addFilterBefore(new JwtFilter(jwtParser, jwtValidator), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(handling -> handling.accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}

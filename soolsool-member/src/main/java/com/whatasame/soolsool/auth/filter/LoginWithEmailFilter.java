package com.whatasame.soolsool.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whatasame.soolsool.auth.command.LoginWithEmail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginWithEmailFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;

    public LoginWithEmailFilter(
            final ObjectMapper objectMapper,
            final AuthenticationManager authenticationManager,
            final AuthenticationSuccessHandler authenticationSuccessHandler,
            final AuthenticationFailureHandler authenticationFailureHandler) {
        super(request -> request.getMethod().matches(HttpMethod.POST.name())
                && request.getRequestURI().matches("/auth/login"));

        this.objectMapper = objectMapper;

        setAuthenticationManager(authenticationManager);
        setAuthenticationSuccessHandler(authenticationSuccessHandler);
        setAuthenticationFailureHandler(authenticationFailureHandler);
    }

    @Override
    @SneakyThrows
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) {
        final LoginWithEmail command = objectMapper.readValue(request.getInputStream(), LoginWithEmail.class);

        final UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(command.email(), command.password());

        return getAuthenticationManager().authenticate(authentication);
    }
}

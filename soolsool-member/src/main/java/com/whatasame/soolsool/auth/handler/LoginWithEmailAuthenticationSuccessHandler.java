package com.whatasame.soolsool.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whatasame.soolsool.jwt.jwt.JwtProvider;
import com.whatasame.soolsool.jwt.jwt.model.AuthToken;
import com.whatasame.soolsool.rest.ApiResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginWithEmailAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(
            final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication)
            throws IOException {
        final AuthToken token = jwtProvider.createToken(authentication);

        final ApiResult<AuthToken> body = ApiResult.succeed(token);
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}

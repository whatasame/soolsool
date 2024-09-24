package com.whatasame.soolsool.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whatasame.soolsool.rest.ApiResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginWithEmailAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(
            final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException e)
            throws IOException, ServletException {
        log.error("이메일을 이용한 로그인 실패 발생", e);
        final ApiResult<String> body = ApiResult.fail("이메일 혹은 비밀번호가 일치하지 않습니다");

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}

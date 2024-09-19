package com.whatasame.soolsool.security.jwt;

import com.whatasame.soolsool.security.jwt.model.AuthToken.AccessToken;
import com.whatasame.soolsool.security.model.MemberAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtParser parser;
    private final JwtValidator validator;

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain)
            throws ServletException, IOException {
        try {
            final AccessToken token = extractToken(request);

            validator.validate(token);

            final long subject = parser.parseSubject(token);
            final List<SimpleGrantedAuthority> authorities = parser.parseAuthorities(token);

            final Authentication auth = new MemberAuthentication(subject, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (final Exception e) {
            log.info("JWT 인증 간에 예외가 발생했습니다. ", e);
        }

        filterChain.doFilter(request, response);
    }

    private AccessToken extractToken(final HttpServletRequest request) {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(header)) {
            throw new IllegalArgumentException("인증 헤더가 누락되었습니다.");
        }
        if (!header.startsWith("Bearer ")) {
            throw new IllegalArgumentException("올바르지 않은 인증 헤더입니다.");
        }

        return new AccessToken(header.substring(7));
    }
}

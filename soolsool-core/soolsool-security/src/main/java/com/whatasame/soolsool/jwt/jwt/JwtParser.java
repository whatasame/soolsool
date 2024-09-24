package com.whatasame.soolsool.jwt.jwt;

import static com.whatasame.soolsool.jwt.jwt.JwtProperties.AUTHORITIES_KEY;

import com.whatasame.soolsool.jwt.jwt.model.AuthToken.AccessToken;
import com.whatasame.soolsool.jwt.jwt.model.AuthToken.RefreshToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtParser {

    private final JwtSecret jwtSecret;

    public JwtParser(final JwtSecret jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public long parseSubject(final AccessToken token) {
        return Long.parseLong(parseAccessToken(token).getSubject());
    }

    public Date parseExpiration(final AccessToken token) {
        return parseAccessToken(token).getExpiration();
    }

    public List<SimpleGrantedAuthority> parseAuthorities(final AccessToken token) {
        final List<String> list = parseAccessToken(token).get(AUTHORITIES_KEY, List.class);

        return list.stream().map(SimpleGrantedAuthority::new).toList();
    }

    private Claims parseAccessToken(final AccessToken token) {
        return Jwts.parser()
                .verifyWith(jwtSecret.getAccessTokenKey()) // Access Token 서명 검증
                .build()
                .parseSignedClaims(token.value())
                .getPayload();
    }

    private Claims parseRefreshToken(final RefreshToken token) {
        return Jwts.parser()
                .verifyWith(jwtSecret.getAccessTokenKey()) // Access Token 서명 검증
                .build()
                .parseSignedClaims(token.value())
                .getPayload();
    }
}
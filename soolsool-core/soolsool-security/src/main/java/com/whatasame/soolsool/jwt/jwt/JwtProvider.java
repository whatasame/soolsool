package com.whatasame.soolsool.jwt.jwt;

import com.whatasame.soolsool.jwt.jwt.model.AuthToken;
import com.whatasame.soolsool.jwt.jwt.model.AuthToken.AccessToken;
import com.whatasame.soolsool.jwt.jwt.model.AuthToken.RefreshToken;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private static final String AUTHORITIES_KEY = "roles";

    private final JwtSecret jwtSecret;

    public AuthToken createToken(final Object principal, final List<String> authorities) {
        return new AuthToken(createAccessToken(principal, authorities), createRefreshToken(principal, authorities));
    }

    private AccessToken createAccessToken(final Object principal, final List<String> authorities) {
        final long now = System.currentTimeMillis();

        final String value = Jwts.builder()
                .subject(principal.toString())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(jwtSecret.getAccessTokenKey())
                .issuedAt(new Date(now))
                .expiration(new Date(now + jwtSecret.accessTokenExpiration()))
                .compact();

        return new AccessToken(value);
    }

    private RefreshToken createRefreshToken(final Object principal, final List<String> authorities) {
        final long now = System.currentTimeMillis();

        final String value = Jwts.builder()
                .subject(principal.toString())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(jwtSecret.getRefreshTokenKey())
                .issuedAt(new Date(now))
                .expiration(new Date(now + jwtSecret.refreshTokenExpiration()))
                .compact();

        return new RefreshToken(value);
    }
}

package com.whatasame.soolsool.security.jwt;

import com.whatasame.soolsool.security.jwt.model.AuthToken;
import com.whatasame.soolsool.security.jwt.model.AuthToken.AccessToken;
import com.whatasame.soolsool.security.jwt.model.AuthToken.RefreshToken;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtSecret jwtSecret;

    public AuthToken createToken(Object principal) {
        return new AuthToken(createAccessToken(principal), createRefreshToken(principal));
    }

    private AccessToken createAccessToken(Object principal) {
        final long now = System.currentTimeMillis();

        String value = Jwts.builder()
                .subject(principal.toString())
                .signWith(jwtSecret.getAccessTokenKey())
                .issuedAt(new Date(now))
                .expiration(new Date(now + jwtSecret.accessTokenExpiration()))
                .compact();

        return new AccessToken(value);
    }

    private RefreshToken createRefreshToken(Object principal) {
        final long now = System.currentTimeMillis();

        String value = Jwts.builder()
                .subject(principal.toString())
                .signWith(jwtSecret.getRefreshTokenKey())
                .issuedAt(new Date(now))
                .expiration(new Date(now + jwtSecret.refreshTokenExpiration()))
                .compact();

        return new RefreshToken(value);
    }
}

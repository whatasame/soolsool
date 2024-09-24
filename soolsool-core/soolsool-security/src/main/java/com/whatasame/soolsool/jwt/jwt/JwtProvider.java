package com.whatasame.soolsool.jwt.jwt;

import static com.whatasame.soolsool.jwt.jwt.JwtProperties.AUTHORITIES_KEY;

import com.whatasame.soolsool.jwt.jwt.model.AuthToken;
import com.whatasame.soolsool.jwt.jwt.model.AuthToken.AccessToken;
import com.whatasame.soolsool.jwt.jwt.model.AuthToken.RefreshToken;
import io.jsonwebtoken.Jwts;
import java.util.Collection;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtSecret jwtSecret;

    public AuthToken createToken(final Authentication authentication) {
        final Object principal = authentication.getPrincipal();
        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        return new AuthToken(createAccessToken(principal, authorities), createRefreshToken(principal, authorities));
    }

    private AccessToken createAccessToken(
            final Object principal, final Collection<? extends GrantedAuthority> authorities) {
        final long now = System.currentTimeMillis();

        final String value = Jwts.builder()
                .subject(principal.toString())
                .claim(
                        AUTHORITIES_KEY,
                        authorities.stream().map(GrantedAuthority::getAuthority).toList())
                .signWith(jwtSecret.getAccessTokenKey())
                .issuedAt(new Date(now))
                .expiration(new Date(now + jwtSecret.accessTokenExpiration()))
                .compact();

        return new AccessToken(value);
    }

    private RefreshToken createRefreshToken(
            final Object principal, final Collection<? extends GrantedAuthority> authorities) {
        final long now = System.currentTimeMillis();

        final String value = Jwts.builder()
                .subject(principal.toString())
                .claim(
                        AUTHORITIES_KEY,
                        authorities.stream().map(GrantedAuthority::getAuthority).toList())
                .signWith(jwtSecret.getRefreshTokenKey())
                .issuedAt(new Date(now))
                .expiration(new Date(now + jwtSecret.refreshTokenExpiration()))
                .compact();

        return new RefreshToken(value);
    }
}

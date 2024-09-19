package com.whatasame.soolsool.security.jwt;

import com.whatasame.soolsool.security.jwt.model.AuthToken.AccessToken;
import com.whatasame.soolsool.security.jwt.model.AuthToken.RefreshToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtParser {

    private final JwtSecret jwtSecret;

    public JwtParser(JwtSecret jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public String parseSubject(AccessToken token) {
        return parseAccessToken(token).getSubject();
    }

    private Claims parseAccessToken(AccessToken token) {
        return Jwts.parser()
                .verifyWith(jwtSecret.getAccessTokenKey()) // Access Token 서명 검증
                .build()
                .parseSignedClaims(token.value())
                .getPayload();
    }

    private Claims parseRefreshToken(RefreshToken token) {
        return Jwts.parser()
                .verifyWith(jwtSecret.getAccessTokenKey()) // Access Token 서명 검증
                .build()
                .parseSignedClaims(token.value())
                .getPayload();
    }
}

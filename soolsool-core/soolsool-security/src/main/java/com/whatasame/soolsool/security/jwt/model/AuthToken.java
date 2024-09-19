package com.whatasame.soolsool.security.jwt.model;

import lombok.Getter;

@Getter
public class AuthToken {

    private final String accessToken;
    private final String refreshToken;

    public AuthToken(final AccessToken accessToken, final RefreshToken refreshToken) {
        this.accessToken = accessToken.value;
        this.refreshToken = refreshToken.value;
    }

    public record AccessToken(String value) {}

    public record RefreshToken(String value) {}
}

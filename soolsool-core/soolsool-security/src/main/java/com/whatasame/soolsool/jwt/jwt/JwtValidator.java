package com.whatasame.soolsool.jwt.jwt;

import com.whatasame.soolsool.jwt.jwt.model.AuthToken.AccessToken;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtValidator {

    private final JwtParser parser;

    public void validate(final AccessToken token) {
        // TODO: Redis에 저장된 토큰이 블랙리스트에 있는지 확인하고, 있다면 인증 거부
        if (isExpired(token)) {
            throw new IllegalArgumentException("만료된 토큰입니다.");
        }
    }

    private boolean isExpired(final AccessToken token) {
        final Date issuedAt = parser.parseIssuedAt(token);

        return issuedAt.before(new Date());
    }
}

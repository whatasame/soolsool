package shop.soolsool.auth.domain;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import shop.soolsool.auth.code.AuthErrorCode;
import shop.soolsool.auth.ui.dto.UserDto;
import shop.soolsool.core.exception.SoolSoolException;
import shop.soolsool.member.domain.model.Member;

@Component
@Slf4j
public class TokenProvider {

    private static final String ROLE_TYPE = "ROLE_TYPE";
    private final String secretKey;
    private final long validityInMilliseconds;

    public TokenProvider(
            @Value("${security.jwt.token.secret-key}") final String secretKey,
            @Value("${security.jwt.token.expire-length}") final long validityInMilliseconds) {
        this.secretKey = secretKey;
        this.validityInMilliseconds = validityInMilliseconds;
    }

    public String createToken(final Member member) {
        final Date now = new Date();
        final Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setSubject(member.getId().toString())
                .setIssuedAt(now)
                .claim(ROLE_TYPE, member.getRoleName())
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public void validateToken(final String token) {
        final Claims body = parseClaimBody(token);
        if (body.getExpiration().before(new Date())) {
            throw new SoolSoolException(AuthErrorCode.TOKEN_ERROR);
        }
    }

    private Claims parseClaimBody(final String token) {
        if (token == null || token.isEmpty()) {
            throw new SoolSoolException(AuthErrorCode.TOKEN_ERROR);
        }
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch (final JwtException | IllegalArgumentException e) {
            throw new SoolSoolException(AuthErrorCode.TOKEN_ERROR);
        }
    }

    public UserDto getUserDto(final String token) {
        final Claims body = parseClaimBody(token);

        // TODO: String이 아닌 MemberRoleType 활용?
        final String authority = (String) body.get(ROLE_TYPE);

        return new UserDto(body.getSubject(), authority);
    }
}

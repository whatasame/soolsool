package shop.soolsool.member.domain.model.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.StringUtils;
import shop.soolsool.core.exception.SoolSoolException;
import shop.soolsool.member.code.MemberErrorCode;

@Getter
@EqualsAndHashCode
public class MemberPassword {

    private static final int MAX_LENGTH = 60;

    private final String password;

    public MemberPassword(final String password) {
        validateIsNotNullOrEmpty(password);
        validateIsValidLength(password);

        this.password = password;
    }

    private void validateIsValidLength(final String password) {
        if (password.length() > MAX_LENGTH) {
            throw new SoolSoolException(MemberErrorCode.INVALID_LENGTH_PASSWORD);
        }
    }

    private void validateIsNotNullOrEmpty(final String password) {
        if (!StringUtils.hasText(password)) {
            throw new SoolSoolException(MemberErrorCode.NO_CONTENT_PASSWORD);
        }
    }

    public boolean matchPassword(final String password) {
        return this.password.equals(password);
    }
}

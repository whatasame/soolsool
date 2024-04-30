package shop.soolsool.statistics.domain.model.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.StringUtils;
import shop.soolsool.core.exception.SoolSoolException;
import shop.soolsool.member.code.MemberErrorCode;

@Getter
@EqualsAndHashCode
public class BrewType {

    private static final int MAX_LENGTH = 20;

    private final String type;

    public BrewType(final String type) {
        validateIsNotNullOrEmpty(type);
        validateIsValidLength(type);

        this.type = type;
    }

    private void validateIsValidLength(final String brewType) {
        if (brewType.length() > MAX_LENGTH) {
            throw new SoolSoolException(MemberErrorCode.INVALID_LENGTH_ADDRESS);
        }
    }

    private void validateIsNotNullOrEmpty(final String brewType) {
        if (!StringUtils.hasText(brewType)) {
            throw new SoolSoolException(MemberErrorCode.NO_CONTENT_ADDRESS);
        }
    }
}

package shop.soolsool.member.domain.model.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.StringUtils;
import shop.soolsool.core.exception.SoolSoolException;
import shop.soolsool.member.code.MemberErrorCode;

@Getter
@EqualsAndHashCode
public class MemberAddress {

    private static final int MAX_LENGTH = 100;

    private final String address;

    public MemberAddress(final String address) {
        validateIsNotNullOrEmpty(address);
        validateIsValidLength(address);

        this.address = address;
    }

    private void validateIsValidLength(final String address) {
        if (address.length() > MAX_LENGTH) {
            throw new SoolSoolException(MemberErrorCode.INVALID_LENGTH_ADDRESS);
        }
    }

    private void validateIsNotNullOrEmpty(final String address) {
        if (!StringUtils.hasText(address)) {
            throw new SoolSoolException(MemberErrorCode.NO_CONTENT_ADDRESS);
        }
    }
}

package shop.soolsool.receipt.domain.model.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.StringUtils;
import shop.soolsool.core.exception.SoolSoolException;
import shop.soolsool.receipt.code.ReceiptErrorCode;

@Getter
@EqualsAndHashCode
public class ReceiptItemName {

    private static final int MAX_LENGTH = 100;

    private final String name;

    public ReceiptItemName(final String name) {
        validateIsNotNullOrEmpty(name);
        validateIsValidLength(name);

        this.name = name;
    }

    private void validateIsValidLength(final String name) {
        if (name.length() > MAX_LENGTH) {
            throw new SoolSoolException(ReceiptErrorCode.INVALID_LENGTH_NAME);
        }
    }

    private void validateIsNotNullOrEmpty(final String name) {
        if (!StringUtils.hasText(name)) {
            throw new SoolSoolException(ReceiptErrorCode.NO_CONTENT_NAME);
        }
    }
}

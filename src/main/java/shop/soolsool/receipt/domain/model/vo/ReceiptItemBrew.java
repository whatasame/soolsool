package shop.soolsool.receipt.domain.model.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.StringUtils;
import shop.soolsool.core.exception.SoolSoolException;
import shop.soolsool.receipt.code.ReceiptErrorCode;

@Getter
@EqualsAndHashCode
public class ReceiptItemBrew {

    private static final int MAX_LENGTH = 20;

    private final String brew;

    public ReceiptItemBrew(final String brew) {
        validateIsNotNullOrEmpty(brew);
        validateIsValidLength(brew);

        this.brew = brew;
    }

    private void validateIsValidLength(final String brew) {
        if (brew.length() > MAX_LENGTH) {
            throw new SoolSoolException(ReceiptErrorCode.INVALID_LENGTH_BREW);
        }
    }

    private void validateIsNotNullOrEmpty(final String brew) {
        if (!StringUtils.hasText(brew)) {
            throw new SoolSoolException(ReceiptErrorCode.NO_CONTENT_BREW);
        }
    }
}
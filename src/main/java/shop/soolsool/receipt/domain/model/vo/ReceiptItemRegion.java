package shop.soolsool.receipt.domain.model.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.StringUtils;
import shop.soolsool.core.exception.SoolSoolException;
import shop.soolsool.receipt.code.ReceiptErrorCode;

@Getter
@EqualsAndHashCode
public class ReceiptItemRegion {

    private static final int MAX_LENGTH = 20;

    private final String region;

    public ReceiptItemRegion(final String region) {
        validateIsNotNullOrEmpty(region);
        validateIsValidLength(region);

        this.region = region;
    }

    private void validateIsValidLength(final String name) {
        if (name.length() > MAX_LENGTH) {
            throw new SoolSoolException(ReceiptErrorCode.INVALID_LENGTH_REGION);
        }
    }

    private void validateIsNotNullOrEmpty(final String name) {
        if (!StringUtils.hasText(name)) {
            throw new SoolSoolException(ReceiptErrorCode.NO_CONTENT_REGION);
        }
    }
}

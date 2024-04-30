package shop.soolsool.receipt.domain.model.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.StringUtils;
import shop.soolsool.core.exception.SoolSoolException;
import shop.soolsool.receipt.code.ReceiptErrorCode;

@Getter
@EqualsAndHashCode
public class ReceiptItemBrand {

    private static final int MAX_LENGTH = 20;

    private final String brand;

    public ReceiptItemBrand(final String brand) {
        validateIsNotNullOrEmpty(brand);
        validateIsValidLength(brand);

        this.brand = brand;
    }

    private void validateIsValidLength(final String brand) {
        if (brand.length() > MAX_LENGTH) {
            throw new SoolSoolException(ReceiptErrorCode.INVALID_LENGTH_BRAND);
        }
    }

    private void validateIsNotNullOrEmpty(final String brand) {
        if (!StringUtils.hasText(brand)) {
            throw new SoolSoolException(ReceiptErrorCode.NO_CONTENT_BRAND);
        }
    }
}

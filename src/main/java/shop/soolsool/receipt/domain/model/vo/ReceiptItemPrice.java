package shop.soolsool.receipt.domain.model.vo;

import java.math.BigInteger;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import shop.soolsool.core.exception.SoolSoolException;
import shop.soolsool.receipt.code.ReceiptErrorCode;

@Getter
@EqualsAndHashCode
public class ReceiptItemPrice {

    private final BigInteger price;

    public ReceiptItemPrice(final BigInteger price) {
        validateIsNotNull(price);
        validateIsValidSize(price);

        this.price = price;
    }

    public static ReceiptItemPrice from(final String price) {
        return new ReceiptItemPrice(new BigInteger(price));
    }

    private void validateIsValidSize(final BigInteger price) {
        if (price.compareTo(BigInteger.ZERO) < 0) {
            throw new SoolSoolException(ReceiptErrorCode.INVALID_SIZE_PRICE);
        }
    }

    private void validateIsNotNull(final BigInteger price) {
        if (Objects.isNull(price)) {
            throw new SoolSoolException(ReceiptErrorCode.NO_CONTENT_PRICE);
        }
    }

    public ReceiptItemPrice subtract(final ReceiptItemPrice mileageUsage) {
        return new ReceiptItemPrice(this.price.subtract(mileageUsage.price));
    }
}

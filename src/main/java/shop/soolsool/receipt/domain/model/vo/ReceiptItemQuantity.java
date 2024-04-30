package shop.soolsool.receipt.domain.model.vo;

import static shop.soolsool.receipt.code.ReceiptErrorCode.INVALID_QUANTITY_SIZE;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import shop.soolsool.core.exception.SoolSoolException;

@Getter
@EqualsAndHashCode
public class ReceiptItemQuantity {

    private final int quantity;

    public ReceiptItemQuantity(final int quantity) {
        validateIsNotLessThanZero(quantity);

        this.quantity = quantity;
    }

    private void validateIsNotLessThanZero(final int quantity) {
        if (quantity < 0) {
            throw new SoolSoolException(INVALID_QUANTITY_SIZE);
        }
    }
}

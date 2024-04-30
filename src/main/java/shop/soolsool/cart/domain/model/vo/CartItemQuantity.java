package shop.soolsool.cart.domain.model.vo;

import static shop.soolsool.cart.code.CartErrorCode.INVALID_QUANTITY_SIZE;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import shop.soolsool.core.exception.SoolSoolException;

@Getter
@EqualsAndHashCode
public class CartItemQuantity {

    private static final int MIN_SIZE = 1;

    private final int quantity;

    public CartItemQuantity(final int quantity) {
        validateIsValidSize(quantity);

        this.quantity = quantity;
    }

    private void validateIsValidSize(final int quantity) {
        if (quantity < MIN_SIZE) {
            throw new SoolSoolException(INVALID_QUANTITY_SIZE);
        }
    }
}

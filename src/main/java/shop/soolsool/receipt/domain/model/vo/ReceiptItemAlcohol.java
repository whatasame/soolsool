package shop.soolsool.receipt.domain.model.vo;

import static shop.soolsool.receipt.code.ReceiptErrorCode.INVALID_SIZE_ALCOHOL;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import shop.soolsool.core.exception.SoolSoolException;

@Getter
@EqualsAndHashCode
@ToString
public class ReceiptItemAlcohol {

    private final double alcohol;

    public ReceiptItemAlcohol(final double alcohol) {
        validateIsGreaterThanZero(alcohol);

        this.alcohol = alcohol;
    }

    private void validateIsGreaterThanZero(final double alcohol) {
        if (alcohol < 0) {
            throw new SoolSoolException(INVALID_SIZE_ALCOHOL);
        }
    }
}

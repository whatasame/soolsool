package shop.soolsool.receipt.domain.model.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import shop.soolsool.core.exception.SoolSoolException;
import shop.soolsool.receipt.code.ReceiptErrorCode;

@Getter
@EqualsAndHashCode
public class ReceiptItemVolume {

    private final int volume;

    public ReceiptItemVolume(final int volume) {
        validateIsNotLessThanZero(volume);

        this.volume = volume;
    }

    private void validateIsNotLessThanZero(final int volume) {
        if (volume < 0) {
            throw new SoolSoolException(ReceiptErrorCode.INVALID_SIZE_VOLUME);
        }
    }
}

package shop.soolsool.liquor.domain.model.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import shop.soolsool.core.exception.SoolSoolException;
import shop.soolsool.liquor.code.LiquorErrorCode;

@Getter
@EqualsAndHashCode
public class LiquorVolume {

    private final int volume;

    public LiquorVolume(final int volume) {
        validateIsNotLessThanZero(volume);

        this.volume = volume;
    }

    private void validateIsNotLessThanZero(final int volume) {
        if (volume < 0) {
            throw new SoolSoolException(LiquorErrorCode.INVALID_SIZE_VOLUME);
        }
    }
}

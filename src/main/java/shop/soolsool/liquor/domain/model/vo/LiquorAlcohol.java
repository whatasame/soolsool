package shop.soolsool.liquor.domain.model.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import shop.soolsool.core.exception.SoolSoolException;
import shop.soolsool.liquor.code.LiquorErrorCode;

@Getter
@EqualsAndHashCode
@ToString
public class LiquorAlcohol {

    private final double alcohol;

    public LiquorAlcohol(final double alcohol) {
        validateIsGreaterThanZero(alcohol);

        this.alcohol = alcohol;
    }

    private void validateIsGreaterThanZero(final double alcohol) {
        if (alcohol < 0) {
            throw new SoolSoolException(LiquorErrorCode.INVALID_SIZE_ALCOHOL);
        }
    }
}

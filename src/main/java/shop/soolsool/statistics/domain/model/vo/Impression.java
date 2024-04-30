package shop.soolsool.statistics.domain.model.vo;

import java.math.BigInteger;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import shop.soolsool.core.exception.SoolSoolException;
import shop.soolsool.member.code.MemberErrorCode;

@Getter
@EqualsAndHashCode
public class Impression {

    private final BigInteger count;

    public Impression(final BigInteger count) {
        validateIsValidSize(count);
        validateIsNotNull(count);

        this.count = count;
    }

    private void validateIsValidSize(final BigInteger impression) {
        if (impression.compareTo(BigInteger.ZERO) < 0) {
            throw new SoolSoolException(MemberErrorCode.INVALID_SIZE_MILEAGE);
        }
    }

    private void validateIsNotNull(final BigInteger impression) {
        if (Objects.isNull(impression)) {
            throw new SoolSoolException(MemberErrorCode.NO_CONTENT_MILEAGE);
        }
    }
}

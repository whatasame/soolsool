package shop.soolsool.member.domain.model.vo;

import java.math.BigInteger;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import shop.soolsool.core.exception.SoolSoolException;
import shop.soolsool.member.code.MemberErrorCode;

@Getter
@EqualsAndHashCode
public class MemberMileage {

    private final BigInteger mileage;

    public MemberMileage(final BigInteger mileage) {
        validateIsNotNull(mileage);
        validateIsValidSize(mileage);

        this.mileage = mileage;
    }

    private void validateIsValidSize(final BigInteger mileage) {
        if (mileage.compareTo(BigInteger.ZERO) < 0) {
            throw new SoolSoolException(MemberErrorCode.INVALID_SIZE_MILEAGE);
        }
    }

    private void validateIsNotNull(final BigInteger mileage) {
        if (Objects.isNull(mileage)) {
            throw new SoolSoolException(MemberErrorCode.NO_CONTENT_MILEAGE);
        }
    }

    public MemberMileage charge(final MemberMileage amount) {
        return new MemberMileage(this.mileage.add(amount.getMileage()));
    }

    public MemberMileage subtract(final MemberMileage other) {
        return new MemberMileage(this.mileage.subtract(other.mileage));
    }

    public boolean isLessThan(final MemberMileage mileageUsage) {
        return this.mileage.compareTo(mileageUsage.mileage) < 0;
    }
}

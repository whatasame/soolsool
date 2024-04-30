package shop.soolsool.statistics.domain.model.vo;

import java.math.BigInteger;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import shop.soolsool.core.exception.SoolSoolException;
import shop.soolsool.member.code.MemberErrorCode;

@Getter
@EqualsAndHashCode
public class SalePrice {

    private final BigInteger price;

    public SalePrice(final BigInteger price) {
        validateIsValidSize(price);
        validateIsNotNull(price);

        this.price = price;
    }

    private void validateIsValidSize(final BigInteger salePrice) {
        if (salePrice.compareTo(BigInteger.ZERO) < 0) {
            throw new SoolSoolException(MemberErrorCode.INVALID_SIZE_MILEAGE);
        }
    }

    private void validateIsNotNull(final BigInteger salePrice) {
        if (Objects.isNull(salePrice)) {
            throw new SoolSoolException(MemberErrorCode.NO_CONTENT_MILEAGE);
        }
    }
}

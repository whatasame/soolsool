package shop.soolsool.statistics.domain.model.converter;

import java.math.BigInteger;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.soolsool.statistics.domain.model.vo.SaleQuantity;

@Converter
public class SaleQuantityConverter implements AttributeConverter<SaleQuantity, BigInteger> {

    @Override
    public BigInteger convertToDatabaseColumn(final SaleQuantity saleQuantity) {
        return saleQuantity.getQuantity();
    }

    @Override
    public SaleQuantity convertToEntityAttribute(final BigInteger dbData) {
        return new SaleQuantity(dbData);
    }
}

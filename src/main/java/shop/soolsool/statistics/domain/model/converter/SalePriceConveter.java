package shop.soolsool.statistics.domain.model.converter;

import java.math.BigInteger;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.soolsool.statistics.domain.model.vo.SalePrice;

@Converter
public class SalePriceConveter implements AttributeConverter<SalePrice, BigInteger> {

    @Override
    public BigInteger convertToDatabaseColumn(final SalePrice salePrice) {
        return salePrice.getPrice();
    }

    @Override
    public SalePrice convertToEntityAttribute(final BigInteger dbData) {
        return new SalePrice(dbData);
    }
}

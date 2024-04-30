package shop.soolsool.liquor.domain.model.converter;

import java.math.BigInteger;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.soolsool.liquor.domain.model.vo.LiquorPrice;

@Converter
public class LiquorPriceConverter implements AttributeConverter<LiquorPrice, BigInteger> {

    @Override
    public BigInteger convertToDatabaseColumn(final LiquorPrice price) {
        return price.getPrice();
    }

    @Override
    public LiquorPrice convertToEntityAttribute(final BigInteger dbData) {
        return new LiquorPrice(dbData);
    }
}

package shop.soolsool.statistics.domain.model.converter;

import java.math.BigInteger;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.soolsool.statistics.domain.model.vo.Impression;

@Converter
public class ImpressionConverter implements AttributeConverter<Impression, BigInteger> {

    @Override
    public BigInteger convertToDatabaseColumn(final Impression impression) {
        return impression.getCount();
    }

    @Override
    public Impression convertToEntityAttribute(final BigInteger dbData) {
        return new Impression(dbData);
    }
}

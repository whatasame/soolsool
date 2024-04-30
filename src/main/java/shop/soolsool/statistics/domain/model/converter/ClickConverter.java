package shop.soolsool.statistics.domain.model.converter;

import java.math.BigInteger;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.soolsool.statistics.domain.model.vo.Click;

@Converter
public class ClickConverter implements AttributeConverter<Click, BigInteger> {

    @Override
    public BigInteger convertToDatabaseColumn(final Click click) {
        return click.getCount();
    }

    @Override
    public Click convertToEntityAttribute(final BigInteger dbData) {
        return new Click(dbData);
    }
}

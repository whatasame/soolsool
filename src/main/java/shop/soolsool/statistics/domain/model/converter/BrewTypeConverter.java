package shop.soolsool.statistics.domain.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.soolsool.statistics.domain.model.vo.BrewType;

@Converter
public class BrewTypeConverter implements AttributeConverter<BrewType, String> {

    @Override
    public String convertToDatabaseColumn(final BrewType brewType) {
        return brewType.getType();
    }

    @Override
    public BrewType convertToEntityAttribute(final String dbData) {
        return new BrewType(dbData);
    }
}

package shop.soolsool.liquor.domain.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.soolsool.liquor.domain.model.vo.LiquorName;

@Converter
public class LiquorNameConverter implements AttributeConverter<LiquorName, String> {

    @Override
    public String convertToDatabaseColumn(final LiquorName name) {
        return name.getName();
    }

    @Override
    public LiquorName convertToEntityAttribute(final String dbData) {
        return new LiquorName(dbData);
    }
}

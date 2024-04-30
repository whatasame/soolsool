package shop.soolsool.liquor.domain.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.soolsool.liquor.domain.model.vo.LiquorAlcohol;

@Converter
public class LiquorAlcoholConverter implements AttributeConverter<LiquorAlcohol, Double> {

    @Override
    public Double convertToDatabaseColumn(final LiquorAlcohol alcohol) {
        return alcohol.getAlcohol();
    }

    @Override
    public LiquorAlcohol convertToEntityAttribute(final Double dbData) {
        return new LiquorAlcohol(dbData);
    }
}

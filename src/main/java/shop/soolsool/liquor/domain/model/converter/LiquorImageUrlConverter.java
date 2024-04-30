package shop.soolsool.liquor.domain.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.soolsool.liquor.domain.model.vo.LiquorImageUrl;

@Converter
public class LiquorImageUrlConverter implements AttributeConverter<LiquorImageUrl, String> {

    @Override
    public String convertToDatabaseColumn(final LiquorImageUrl imageUrl) {
        return imageUrl.getImageUrl();
    }

    @Override
    public LiquorImageUrl convertToEntityAttribute(final String dbData) {
        return new LiquorImageUrl(dbData);
    }
}

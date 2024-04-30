package shop.soolsool.liquor.domain.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.soolsool.liquor.domain.model.vo.LiquorBrand;

@Converter
public class LiquorBrandConverter implements AttributeConverter<LiquorBrand, String> {

    @Override
    public String convertToDatabaseColumn(final LiquorBrand brand) {
        return brand.getBrand();
    }

    @Override
    public LiquorBrand convertToEntityAttribute(final String dbData) {
        return new LiquorBrand(dbData);
    }
}

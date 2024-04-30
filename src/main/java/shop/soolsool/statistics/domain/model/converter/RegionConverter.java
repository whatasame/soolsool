package shop.soolsool.statistics.domain.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.soolsool.statistics.domain.model.vo.Region;

@Converter
public class RegionConverter implements AttributeConverter<Region, String> {

    @Override
    public String convertToDatabaseColumn(final Region attribute) {
        return attribute.getName();
    }

    @Override
    public Region convertToEntityAttribute(final String dbData) {
        return new Region(dbData);
    }
}

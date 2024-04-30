package shop.soolsool.liquor.domain.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.soolsool.liquor.domain.model.vo.LiquorVolume;

@Converter
public class LiquorVolumeConverter implements AttributeConverter<LiquorVolume, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final LiquorVolume volume) {
        return volume.getVolume();
    }

    @Override
    public LiquorVolume convertToEntityAttribute(final Integer dbData) {
        return new LiquorVolume(dbData);
    }
}

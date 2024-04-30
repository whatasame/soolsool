package shop.soolsool.liquor.domain.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.soolsool.liquor.domain.model.vo.LiquorCtrImpression;

@Converter
public class LiquorCtrImpressionConverter implements AttributeConverter<LiquorCtrImpression, Long> {

    @Override
    public Long convertToDatabaseColumn(final LiquorCtrImpression impression) {
        return impression.getImpression();
    }

    @Override
    public LiquorCtrImpression convertToEntityAttribute(final Long dbData) {
        return new LiquorCtrImpression(dbData);
    }
}

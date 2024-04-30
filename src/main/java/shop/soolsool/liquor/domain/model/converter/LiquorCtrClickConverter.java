package shop.soolsool.liquor.domain.model.converter;

import javax.persistence.AttributeConverter;
import shop.soolsool.liquor.domain.model.vo.LiquorCtrClick;

public class LiquorCtrClickConverter implements AttributeConverter<LiquorCtrClick, Long> {

    @Override
    public Long convertToDatabaseColumn(final LiquorCtrClick click) {
        return click.getCount();
    }

    @Override
    public LiquorCtrClick convertToEntityAttribute(final Long dbData) {
        return new LiquorCtrClick(dbData);
    }
}

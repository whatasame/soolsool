package shop.soolsool.liquor.domain.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.soolsool.liquor.domain.model.vo.LiquorStockCount;

@Converter
public class LiquorStockCountConverter implements AttributeConverter<LiquorStockCount, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final LiquorStockCount stock) {
        return stock.getStock();
    }

    @Override
    public LiquorStockCount convertToEntityAttribute(final Integer dbData) {
        return new LiquorStockCount(dbData);
    }
}

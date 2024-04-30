package shop.soolsool.receipt.domain.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.soolsool.receipt.domain.model.vo.ReceiptItemRegion;

@Converter
public class ReceiptItemRegionConverter implements AttributeConverter<ReceiptItemRegion, String> {

    @Override
    public String convertToDatabaseColumn(final ReceiptItemRegion brand) {
        return brand.getRegion();
    }

    @Override
    public ReceiptItemRegion convertToEntityAttribute(final String dbData) {
        return new ReceiptItemRegion(dbData);
    }
}

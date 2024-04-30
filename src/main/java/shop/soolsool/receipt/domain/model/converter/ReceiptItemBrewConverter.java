package shop.soolsool.receipt.domain.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.soolsool.receipt.domain.model.vo.ReceiptItemBrew;

@Converter
public class ReceiptItemBrewConverter implements AttributeConverter<ReceiptItemBrew, String> {

    @Override
    public String convertToDatabaseColumn(final ReceiptItemBrew brand) {
        return brand.getBrew();
    }

    @Override
    public ReceiptItemBrew convertToEntityAttribute(final String dbData) {
        return new ReceiptItemBrew(dbData);
    }
}

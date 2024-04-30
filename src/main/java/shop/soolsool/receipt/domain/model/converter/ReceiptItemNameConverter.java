package shop.soolsool.receipt.domain.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.soolsool.receipt.domain.model.vo.ReceiptItemName;

@Converter
public class ReceiptItemNameConverter implements AttributeConverter<ReceiptItemName, String> {

    @Override
    public String convertToDatabaseColumn(final ReceiptItemName name) {
        return name.getName();
    }

    @Override
    public ReceiptItemName convertToEntityAttribute(final String dbData) {
        return new ReceiptItemName(dbData);
    }
}

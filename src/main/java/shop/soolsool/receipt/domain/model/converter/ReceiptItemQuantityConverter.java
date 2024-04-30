package shop.soolsool.receipt.domain.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.soolsool.receipt.domain.model.vo.ReceiptItemQuantity;

@Converter
public class ReceiptItemQuantityConverter implements AttributeConverter<ReceiptItemQuantity, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final ReceiptItemQuantity quantity) {
        return quantity.getQuantity();
    }

    @Override
    public ReceiptItemQuantity convertToEntityAttribute(final Integer dbData) {
        return new ReceiptItemQuantity(dbData);
    }
}

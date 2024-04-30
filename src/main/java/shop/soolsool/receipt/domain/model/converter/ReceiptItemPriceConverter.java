package shop.soolsool.receipt.domain.model.converter;

import java.math.BigInteger;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.soolsool.receipt.domain.model.vo.ReceiptItemPrice;

@Converter
public class ReceiptItemPriceConverter implements AttributeConverter<ReceiptItemPrice, BigInteger> {

    @Override
    public BigInteger convertToDatabaseColumn(final ReceiptItemPrice price) {
        return price.getPrice();
    }

    @Override
    public ReceiptItemPrice convertToEntityAttribute(final BigInteger dbData) {
        return new ReceiptItemPrice(dbData);
    }
}

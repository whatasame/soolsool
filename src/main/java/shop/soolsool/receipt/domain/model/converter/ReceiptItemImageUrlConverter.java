package shop.soolsool.receipt.domain.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.soolsool.receipt.domain.model.vo.ReceiptItemImageUrl;

@Converter
public class ReceiptItemImageUrlConverter implements AttributeConverter<ReceiptItemImageUrl, String> {

    @Override
    public String convertToDatabaseColumn(final ReceiptItemImageUrl imageUrl) {
        return imageUrl.getImageUrl();
    }

    @Override
    public ReceiptItemImageUrl convertToEntityAttribute(final String dbData) {
        return new ReceiptItemImageUrl(dbData);
    }
}

package shop.soolsool.receipt.domain.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.soolsool.receipt.domain.model.vo.ReceiptItemBrand;

@Converter
public class ReceiptItemBrandConverter implements AttributeConverter<ReceiptItemBrand, String> {

    @Override
    public String convertToDatabaseColumn(final ReceiptItemBrand brand) {
        return brand.getBrand();
    }

    @Override
    public ReceiptItemBrand convertToEntityAttribute(final String dbData) {
        return new ReceiptItemBrand(dbData);
    }
}

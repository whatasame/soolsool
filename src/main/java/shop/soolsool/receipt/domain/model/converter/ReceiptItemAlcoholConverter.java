package shop.soolsool.receipt.domain.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.soolsool.receipt.domain.model.vo.ReceiptItemAlcohol;

@Converter
public class ReceiptItemAlcoholConverter implements AttributeConverter<ReceiptItemAlcohol, Double> {

    @Override
    public Double convertToDatabaseColumn(final ReceiptItemAlcohol alcohol) {
        return alcohol.getAlcohol();
    }

    @Override
    public ReceiptItemAlcohol convertToEntityAttribute(final Double dbData) {
        return new ReceiptItemAlcohol(dbData);
    }
}

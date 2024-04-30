package shop.soolsool.receipt.domain.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.soolsool.receipt.domain.model.vo.ReceiptItemVolume;

@Converter
public class ReceiptItemVolumeConverter implements AttributeConverter<ReceiptItemVolume, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final ReceiptItemVolume volume) {
        return volume.getVolume();
    }

    @Override
    public ReceiptItemVolume convertToEntityAttribute(final Integer dbData) {
        return new ReceiptItemVolume(dbData);
    }
}

package shop.soolsool.receipt.domain.model.converter;

import java.util.Arrays;
import java.util.Objects;
import org.springframework.core.convert.converter.Converter;
import shop.soolsool.receipt.domain.model.vo.ReceiptStatusType;

public class ReceiptStatusTypeConverter implements Converter<String, ReceiptStatusType> {

    // TODO: null이 돌아다니는데 어떻게 하면 안전하게 할 수 있을까?
    @Override
    public ReceiptStatusType convert(final String statusType) {
        return Arrays.stream(ReceiptStatusType.values())
                .filter(value -> Objects.equals(value.getName(), statusType))
                .findFirst()
                .orElse(null);
    }
}

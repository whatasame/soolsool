package shop.soolsool.liquor.config;

import java.util.Arrays;
import java.util.Objects;
import org.springframework.core.convert.converter.Converter;
import shop.soolsool.liquor.domain.model.vo.LiquorBrewType;

public class LiquorBrewTypeConverter implements Converter<String, LiquorBrewType> {

    // TODO: null이 돌아다니는데 어떻게 하면 안전하게 할 수 있을까?
    @Override
    public LiquorBrewType convert(final String brewType) {
        return Arrays.stream(LiquorBrewType.values())
                .filter(value -> Objects.equals(value.getName(), brewType))
                .findFirst()
                .orElse(null);
    }
}

package shop.soolsool.cart.domain.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.soolsool.cart.domain.model.vo.CartItemQuantity;

@Converter
public class CartItemQuantityConverter implements AttributeConverter<CartItemQuantity, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final CartItemQuantity quantity) {
        return quantity.getQuantity();
    }

    @Override
    public CartItemQuantity convertToEntityAttribute(final Integer dbData) {
        return new CartItemQuantity(dbData);
    }
}

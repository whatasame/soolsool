package shop.soolsool.cart.ui.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shop.soolsool.cart.domain.model.CartItem;

@Getter
@RequiredArgsConstructor
public class CartItemResponse {

    private final Long id;
    private final String liquorName;
    private final String liquorPrice;
    private final String imageUrl;
    private final Integer liquorQuantity;

    public static CartItemResponse from(final CartItem cartItem) {
        return new CartItemResponse(
                cartItem.getId(),
                cartItem.getLiquor().getName(),
                cartItem.getLiquor().getPrice().toString(),
                cartItem.getLiquor().getImageUrl(),
                cartItem.getQuantity());
    }
}

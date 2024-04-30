package shop.soolsool.cart.ui.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class CartItemSaveRequest {

    private final Long liquorId;
    private final Integer quantity;
}

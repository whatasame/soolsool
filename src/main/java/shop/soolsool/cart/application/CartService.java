package shop.soolsool.cart.application;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.soolsool.cart.code.CartErrorCode;
import shop.soolsool.cart.domain.model.Cart;
import shop.soolsool.cart.domain.model.CartItem;
import shop.soolsool.cart.domain.repository.CartItemRepository;
import shop.soolsool.cart.ui.dto.request.CartItemModifyRequest;
import shop.soolsool.cart.ui.dto.request.CartItemSaveRequest;
import shop.soolsool.cart.ui.dto.response.CartItemResponse;
import shop.soolsool.core.exception.SoolSoolException;
import shop.soolsool.liquor.domain.model.Liquor;
import shop.soolsool.liquor.domain.repository.LiquorRepository;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final LiquorRepository liquorRepository;

    @Transactional
    public Long addCartItem(final Long memberId, final CartItemSaveRequest request) {
        final Liquor liquor = liquorRepository
                .findById(request.getLiquorId())
                .orElseThrow(() -> new SoolSoolException(CartErrorCode.NOT_FOUND_LIQUOR));

        final CartItem newCartItem = CartItem.builder()
                .memberId(memberId)
                .liquor(liquor)
                .quantity(request.getQuantity())
                .build();

        final Cart cart = new Cart(memberId, findAllByMemberIdOrderByCreatedAtDesc(memberId));
        cart.addCartItem(newCartItem);

        return cartItemRepository.save(newCartItem).getId();
    }

    private List<CartItem> findAllByMemberIdOrderByCreatedAtDesc(final Long memberId) {
        return cartItemRepository.findAllByMemberIdOrderByCreatedAtDesc(memberId);
    }

    @Transactional(readOnly = true)
    public List<CartItemResponse> cartItemList(final Long memberId) {
        final List<CartItem> cartItems = cartItemRepository.findAllByMemberIdOrderByCreatedAtDescWithLiquor(memberId);

        return cartItems.stream().map(CartItemResponse::from).collect(Collectors.toList());
    }

    @Transactional
    public void modifyCartItemQuantity(
            final Long memberId, final Long cartItemId, final CartItemModifyRequest cartItemModifyRequest) {
        final CartItem cartItem = cartItemRepository
                .findById(cartItemId)
                .orElseThrow(() -> new SoolSoolException(CartErrorCode.NOT_FOUND_CART_ITEM));

        validateMemberId(memberId, cartItem.getMemberId());

        cartItem.updateQuantity(cartItemModifyRequest.getLiquorQuantity());
    }

    @Transactional
    public void removeCartItem(final Long memberId, final Long cartItemId) {
        final CartItem cartItem = cartItemRepository
                .findById(cartItemId)
                .orElseThrow(() -> new SoolSoolException(CartErrorCode.NOT_FOUND_CART_ITEM));

        validateMemberId(memberId, cartItem.getMemberId());

        cartItemRepository.delete(cartItem);
    }

    private void validateMemberId(final Long memberId, final Long cartItemMemberId) {
        if (!Objects.equals(cartItemMemberId, memberId)) {
            throw new SoolSoolException(CartErrorCode.NOT_EQUALS_MEMBER);
        }
    }

    @Transactional
    public void removeCartItems(final Long memberId) {
        cartItemRepository.deleteAllByMemberId(memberId);
    }
}

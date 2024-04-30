package shop.soolsool.receipt.application;

import static shop.soolsool.cart.code.CartErrorCode.NOT_FOUND_CART_ITEM;
import static shop.soolsool.receipt.code.ReceiptErrorCode.NOT_RECEIPT_TYPE_FOUND;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.soolsool.cart.domain.model.Cart;
import shop.soolsool.cart.domain.model.CartItem;
import shop.soolsool.core.exception.SoolSoolException;
import shop.soolsool.receipt.domain.model.Receipt;
import shop.soolsool.receipt.domain.model.ReceiptItem;
import shop.soolsool.receipt.domain.model.ReceiptStatus;
import shop.soolsool.receipt.domain.model.vo.ReceiptItemPrice;
import shop.soolsool.receipt.domain.model.vo.ReceiptItemQuantity;
import shop.soolsool.receipt.domain.model.vo.ReceiptStatusType;
import shop.soolsool.receipt.domain.repository.ReceiptStatusCache;

@Component
@RequiredArgsConstructor
public class ReceiptMapper {

    private static final BigInteger MILEAGE_USAGE_PERCENT = BigInteger.valueOf(10L);

    private final ReceiptStatusCache receiptStatusRepository;

    public Receipt mapFrom(final Cart cart, final BigInteger mileage) {
        if (cart.isEmpty()) {
            throw new SoolSoolException(NOT_FOUND_CART_ITEM);
        }

        final ReceiptItemPrice totalPrice = computeTotalPrice(cart);
        final ReceiptItemPrice mileageUsage = computeMileageUsage(mileage);

        return Receipt.builder()
                .memberId(cart.getMemberId())
                .receiptStatus(getReceiptStatusByType(ReceiptStatusType.INPROGRESS))
                .originalTotalPrice(totalPrice)
                .mileageUsage(mileageUsage)
                .purchasedTotalPrice(totalPrice.subtract(mileageUsage))
                .totalQuantity(new ReceiptItemQuantity(cart.getCartItemSize()))
                .receiptItems(mapToReceiptItems(cart))
                .build();
    }

    private ReceiptItemPrice computeMileageUsage(final BigInteger mileage) {
        return new ReceiptItemPrice(mileage.divide(MILEAGE_USAGE_PERCENT));
    }

    private List<ReceiptItem> mapToReceiptItems(final Cart cart) {
        return cart.getCartItems().stream()
                .map(cartItem -> ReceiptItem.of(cartItem.getLiquor(), cartItem.getQuantity()))
                .collect(Collectors.toList());
    }

    private ReceiptItemPrice computeTotalPrice(final Cart cart) {
        BigInteger totalPrice = BigInteger.ZERO;

        for (final CartItem cartItem : cart.getCartItems()) {
            totalPrice = totalPrice.add(cartItem.getLiquorPrice());
        }

        return new ReceiptItemPrice(totalPrice);
    }

    private ReceiptStatus getReceiptStatusByType(final ReceiptStatusType type) {
        return receiptStatusRepository
                .findByType(type)
                .orElseThrow(() -> new SoolSoolException(NOT_RECEIPT_TYPE_FOUND));
    }
}

package shop.soolsool.order.ui.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shop.soolsool.receipt.domain.model.ReceiptItem;

@Getter
@RequiredArgsConstructor
public class OrderItemListResponse {

    private final Long liquorId;
    private final String liquorName;
    private final String liquorImageUrl;

    public static OrderItemListResponse from(final ReceiptItem receiptItem) {
        return new OrderItemListResponse(
                receiptItem.getLiquorId(), receiptItem.getReceiptItemName(), receiptItem.getReceiptItemImageUrl());
    }
}

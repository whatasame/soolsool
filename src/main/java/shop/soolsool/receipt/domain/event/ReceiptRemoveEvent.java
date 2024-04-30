package shop.soolsool.receipt.domain.event;

import lombok.Getter;

@Getter
public class ReceiptRemoveEvent {

    private final Long receiptId;

    public ReceiptRemoveEvent(final Long receiptId) {
        this.receiptId = receiptId;
    }
}

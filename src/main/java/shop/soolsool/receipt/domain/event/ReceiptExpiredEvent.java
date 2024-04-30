package shop.soolsool.receipt.domain.event;

import lombok.Getter;

@Getter
public class ReceiptExpiredEvent {

    private final Long receiptId;
    private final Long memberId;

    public ReceiptExpiredEvent(final Long receiptId, final Long memberId) {
        this.receiptId = receiptId;
        this.memberId = memberId;
    }
}

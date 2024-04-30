package shop.soolsool.receipt.domain.event.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import shop.soolsool.receipt.application.ReceiptService;
import shop.soolsool.receipt.domain.event.ReceiptExpiredEvent;
import shop.soolsool.receipt.domain.model.vo.ReceiptStatusType;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReceiptEventListener {

    private final ReceiptService receiptService;

    @Async
    @EventListener
    public void expireReceipt(final ReceiptExpiredEvent event) {
        receiptService.modifyReceiptStatus(event.getMemberId(), event.getReceiptId(), ReceiptStatusType.EXPIRED);

        log.info("Member {}'s Receipt {} Expired", event.getMemberId(), event.getReceiptId());
    }
}

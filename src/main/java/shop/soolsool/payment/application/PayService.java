package shop.soolsool.payment.application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.soolsool.cart.application.CartService;
import shop.soolsool.core.exception.SoolSoolException;
import shop.soolsool.core.infra.LockType;
import shop.soolsool.liquor.application.LiquorService;
import shop.soolsool.liquor.application.LiquorStockService;
import shop.soolsool.member.application.MemberMileageService;
import shop.soolsool.order.application.OrderService;
import shop.soolsool.order.domain.model.Order;
import shop.soolsool.payment.code.PayErrorCode;
import shop.soolsool.payment.infrastructure.PayClient;
import shop.soolsool.payment.ui.dto.request.PayOrderRequest;
import shop.soolsool.payment.ui.dto.response.PayReadyResponse;
import shop.soolsool.receipt.application.ReceiptService;
import shop.soolsool.receipt.domain.event.ReceiptRemoveEvent;
import shop.soolsool.receipt.domain.model.Receipt;
import shop.soolsool.receipt.domain.model.ReceiptItem;
import shop.soolsool.receipt.domain.model.vo.ReceiptStatusType;

@Service
@RequiredArgsConstructor
public class PayService {

    private static final long LOCK_WAIT_TIME = 3L;
    private static final long LOCK_LEASE_TIME = 3L;

    private final ReceiptService receiptService;
    private final MemberMileageService memberMileageService;
    private final OrderService orderService;
    private final CartService cartService;
    private final LiquorStockService liquorStockService;
    private final LiquorService liquorService;

    private final PayClient payClient;

    private final ApplicationEventPublisher publisher;

    private final RedissonClient redissonClient;

    @Transactional
    public PayReadyResponse ready(final Long memberId, final PayOrderRequest payOrderRequest) {
        final Receipt receipt = receiptService.getMemberReceipt(memberId, payOrderRequest.getReceiptId());

        return payClient.ready(receipt);
    }

    @Transactional
    public Order approve(final Long memberId, final Long receiptId, final String pgToken) {
        final Receipt receipt = receiptService.getMemberReceipt(memberId, receiptId);

        final List<RLock> locks = new ArrayList<>();
        locks.add(getMemberLock(memberId));
        locks.add(getReceiptLock(receiptId));
        locks.addAll(getLiquorLocks(receipt.getReceiptItems()));

        final RLock multiLock = redissonClient.getMultiLock(locks.toArray(locks.toArray(new RLock[0])));

        try {
            multiLock.tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS);

            decreaseStocks(receipt);

            final Order order = orderService.addOrder(memberId, receipt);

            memberMileageService.subtractMemberMileage(memberId, order, receipt.getMileageUsage());

            cartService.removeCartItems(memberId);

            receiptService.modifyReceiptStatus(memberId, receiptId, ReceiptStatusType.COMPLETED);

            orderService.addPaymentInfo(payClient.payApprove(receipt, pgToken).toEntity(order.getId()));

            publisher.publishEvent(new ReceiptRemoveEvent(receiptId));

            return order;
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();

            throw new SoolSoolException(PayErrorCode.INTERRUPTED_THREAD);
        } finally {
            multiLock.unlock();
        }
    }

    private RLock getMemberLock(final Long memberId) {
        return redissonClient.getLock(LockType.MEMBER.getPrefix() + memberId);
    }

    private RLock getReceiptLock(final Long receiptId) {
        return redissonClient.getLock(LockType.RECEIPT.getPrefix() + receiptId);
    }

    private List<RLock> getLiquorLocks(final List<ReceiptItem> receiptItems) {
        return receiptItems.stream()
                .map(ReceiptItem::getLiquorId)
                .sorted()
                .map(liquorId -> redissonClient.getLock(LockType.LIQUOR_STOCK.getPrefix() + liquorId))
                .collect(Collectors.toList());
    }

    private void decreaseStocks(final Receipt receipt) {
        for (final ReceiptItem receiptItem : receipt.getReceiptItems()) {
            liquorStockService.decreaseLiquorStock(receiptItem.getLiquorId(), receiptItem.getQuantity());
            liquorService.decreaseTotalStock(receiptItem.getLiquorId(), receiptItem.getQuantity());
        }
    }

    @Transactional
    public void cancelReceipt(final Long memberId, final Long receiptId) {
        receiptService.modifyReceiptStatus(memberId, receiptId, ReceiptStatusType.CANCELED);
    }
}

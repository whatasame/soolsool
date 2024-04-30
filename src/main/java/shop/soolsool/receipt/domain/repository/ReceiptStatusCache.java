package shop.soolsool.receipt.domain.repository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import shop.soolsool.receipt.domain.model.ReceiptStatus;
import shop.soolsool.receipt.domain.model.vo.ReceiptStatusType;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReceiptStatusCache {

    private final ReceiptStatusRepository receiptStatusRepository;

    @Cacheable(
            value = "receiptStatus",
            key = "#receiptStatusType",
            unless = "#result==null",
            cacheManager = "caffeineCacheManager")
    public Optional<ReceiptStatus> findByType(final ReceiptStatusType receiptStatusType) {
        log.info("ReceiptStatusCache {}", receiptStatusType);
        return receiptStatusRepository.findByType(receiptStatusType);
    }
}

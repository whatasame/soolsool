package shop.soolsool.order.domain.repository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import shop.soolsool.order.domain.model.OrderStatus;
import shop.soolsool.order.domain.model.vo.OrderStatusType;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderStatusCache {

    private final OrderStatusRepository orderStatusRepository;

    @Cacheable(value = "orderStatus", key = "#type", unless = "#result==null", cacheManager = "caffeineCacheManager")
    public Optional<OrderStatus> findByType(final OrderStatusType type) {
        log.info("OrderStatusCache {}", type);
        return orderStatusRepository.findByType(type);
    }
}

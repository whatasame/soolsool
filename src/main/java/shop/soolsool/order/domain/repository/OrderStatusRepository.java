package shop.soolsool.order.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.soolsool.order.domain.model.OrderStatus;
import shop.soolsool.order.domain.model.vo.OrderStatusType;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {

    Optional<OrderStatus> findByType(final OrderStatusType type);
}

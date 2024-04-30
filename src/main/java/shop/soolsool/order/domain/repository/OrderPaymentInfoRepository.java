package shop.soolsool.order.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.soolsool.order.domain.model.OrderPaymentInfo;

@Repository
public interface OrderPaymentInfoRepository extends JpaRepository<OrderPaymentInfo, Long> {

    @Query("select p from OrderPaymentInfo p inner join Order o on p.orderId = o.id and o.id = :orderId")
    Optional<OrderPaymentInfo> findPaymentInfoByOrderId(final Long orderId);
}

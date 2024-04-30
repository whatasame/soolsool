package shop.soolsool.order.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import shop.soolsool.core.exception.SoolSoolException;
import shop.soolsool.order.code.OrderErrorCode;
import shop.soolsool.order.domain.model.OrderStatus;
import shop.soolsool.order.domain.model.vo.OrderStatusType;

@DataJpaTest
@Sql("/order-type.sql")
@DisplayName("통합 테스트: OrderStatusRepository")
class OrderStatusTypeRepositoryTest {

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Test
    @DisplayName("OrderStatusType으로 OrderStatus를 조회한다.")
    void findByLiquorRegionType_type() {
        // given
        final OrderStatus 결제완료 = orderStatusRepository
                .findByType(OrderStatusType.COMPLETED)
                .orElseThrow(() -> new SoolSoolException(OrderErrorCode.NOT_LIQUOR_STATUS_FOUND));

        // when & then
        assertThat(결제완료.getType().getStatus()).isEqualTo(OrderStatusType.COMPLETED.getStatus());
    }
}

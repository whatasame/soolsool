package shop.soolsool.order.domain.repository;

import static shop.soolsool.order.domain.model.QOrder.order;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import shop.soolsool.order.ui.response.OrderListResponse;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<OrderListResponse> findAllByMemberId(
            final Long memberId, final Pageable pageable, final Long cursorId) {
        return queryFactory
                .select(Projections.constructor(OrderListResponse.class, order))
                .from(order)
                .join(order.status)
                .fetchJoin()
                .join(order.receipt)
                .fetchJoin()
                .where(order.memberId.eq(memberId), cursorId(cursorId))
                .orderBy(order.id.desc())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression cursorId(final Long cursorId) {
        if (cursorId == null) {
            return null;
        }
        return order.id.lt(cursorId);
    }
}

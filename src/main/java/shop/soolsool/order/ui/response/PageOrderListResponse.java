package shop.soolsool.order.ui.response;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PageOrderListResponse {

    private final boolean hasNext;
    private final Long cursorId;
    private final List<OrderListResponse> orderListResponses;

    public static PageOrderListResponse of(
            final boolean hasNext, final Long cursorId, final List<OrderListResponse> orderListResponses) {
        return new PageOrderListResponse(hasNext, cursorId, orderListResponses);
    }

    public static PageOrderListResponse of(final boolean hasNext, final List<OrderListResponse> orderListResponses) {
        return new PageOrderListResponse(hasNext, null, orderListResponses);
    }
}

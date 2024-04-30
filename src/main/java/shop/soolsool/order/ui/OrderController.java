package shop.soolsool.order.ui;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.soolsool.auth.ui.dto.LoginUser;
import shop.soolsool.auth.ui.dto.NoAuth;
import shop.soolsool.core.aop.RequestLogging;
import shop.soolsool.core.common.ApiResponse;
import shop.soolsool.order.application.OrderService;
import shop.soolsool.order.code.OrderResultCode;
import shop.soolsool.order.ui.response.OrderDetailResponse;
import shop.soolsool.order.ui.response.OrderRatioResponse;
import shop.soolsool.order.ui.response.PageOrderListResponse;

@RestController
@Slf4j
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @RequestLogging
    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderDetailResponse>> orderDetail(
            @LoginUser final Long memberId, @PathVariable final Long orderId) {
        final OrderDetailResponse response = orderService.orderDetail(memberId, orderId);

        return ResponseEntity.ok(ApiResponse.of(OrderResultCode.ORDER_DETAIL_SUCCESS, response));
    }

    @RequestLogging
    @GetMapping
    public ResponseEntity<ApiResponse<PageOrderListResponse>> orderList(
            @LoginUser final Long memberId,
            @PageableDefault final Pageable pageable,
            @RequestParam(required = false) final Long cursorId) {
        final PageOrderListResponse response = orderService.orderList(memberId, pageable, cursorId);

        return ResponseEntity.ok(ApiResponse.of(OrderResultCode.ORDER_DETAIL_SUCCESS, response));
    }

    @NoAuth
    @RequestLogging
    @GetMapping("/ratio")
    public ResponseEntity<ApiResponse<OrderRatioResponse>> getOrderRatioByLiquorId(@RequestParam final Long liquorId) {
        final Double ratio = orderService.getOrderRatioByLiquorId(liquorId);

        return ResponseEntity.ok(ApiResponse.of(OrderResultCode.ORDER_RATIO_SUCCESS, new OrderRatioResponse(ratio)));
    }

    @RequestLogging
    @PatchMapping("/cancel/{orderId}")
    public ResponseEntity<ApiResponse<Void>> cancelOrder(
            @LoginUser final Long memberId, @PathVariable final Long orderId) {
        orderService.cancelOrder(memberId, orderId);

        return ResponseEntity.ok(ApiResponse.from(OrderResultCode.ORDER_CANCEL_SUCCESS));
    }
}

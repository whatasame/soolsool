package shop.soolsool.payment.ui;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.soolsool.auth.ui.dto.LoginUser;
import shop.soolsool.auth.ui.dto.NoAuth;
import shop.soolsool.core.aop.RequestLogging;
import shop.soolsool.core.common.ApiResponse;
import shop.soolsool.order.domain.model.Order;
import shop.soolsool.payment.application.PayService;
import shop.soolsool.payment.code.PayResultCode;
import shop.soolsool.payment.ui.dto.request.PayOrderRequest;
import shop.soolsool.payment.ui.dto.response.PayReadyResponse;
import shop.soolsool.payment.ui.dto.response.PaySuccessResponse;

@RestController
@Slf4j
@RequestMapping("/pay")
@RequiredArgsConstructor
public class PaymentController {

    private final PayService payService;

    @RequestLogging
    @PostMapping("/ready")
    public ResponseEntity<ApiResponse<PayReadyResponse>> payReady(
            @LoginUser final Long memberId, @RequestBody final PayOrderRequest payOrderRequest) {
        return ResponseEntity.ok(
                ApiResponse.of(PayResultCode.PAY_READY_SUCCESS, payService.ready(memberId, payOrderRequest)));
    }

    @NoAuth
    @RequestLogging
    @GetMapping("/success/{receiptId}")
    public ResponseEntity<ApiResponse<PaySuccessResponse>> kakaoPaySuccess(
            @LoginUser final Long memberId,
            @PathVariable("receiptId") final Long receiptId,
            @RequestParam("pg_token") final String pgToken) {
        final Order order = payService.approve(memberId, receiptId, pgToken);

        return ResponseEntity.ok(
                ApiResponse.of(PayResultCode.PAY_READY_SUCCESS, new PaySuccessResponse(order.getId())));
    }

    @NoAuth
    @RequestLogging
    @GetMapping("/cancel/{receiptId}")
    public ResponseEntity<ApiResponse<Long>> kakaoPayCancel(
            @LoginUser final Long memberId, @PathVariable final Long receiptId) {
        payService.cancelReceipt(memberId, receiptId);

        return ResponseEntity.ok(ApiResponse.from(PayResultCode.PAY_READY_CANCEL));
    }

    @NoAuth
    @RequestLogging
    @GetMapping("/fail/{receiptId}")
    public ResponseEntity<ApiResponse<Long>> kakaoPayFail(
            @LoginUser final Long memberId, @PathVariable final Long receiptId) {
        payService.cancelReceipt(memberId, receiptId);

        return ResponseEntity.ok(ApiResponse.from(PayResultCode.PAY_READY_FAIL));
    }
}

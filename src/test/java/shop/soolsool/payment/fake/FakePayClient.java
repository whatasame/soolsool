package shop.soolsool.payment.fake;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import shop.soolsool.payment.infrastructure.PayClient;
import shop.soolsool.payment.ui.dto.response.PayApproveResponse;
import shop.soolsool.payment.ui.dto.response.PayReadyResponse;
import shop.soolsool.receipt.domain.model.Receipt;

@Primary
@Component
public class FakePayClient implements PayClient {

    @Override
    public PayReadyResponse ready(final Receipt receipt) {
        return new PayReadyResponse("1", "http://pc-url", "http://mobile-url", "http://app-url");
    }

    @Override
    public PayApproveResponse payApprove(final Object... args) {
        return new PayApproveResponse("1", "1", "1", "1", "1", "1");
    }
}

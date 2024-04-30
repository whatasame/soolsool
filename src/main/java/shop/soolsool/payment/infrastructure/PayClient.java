package shop.soolsool.payment.infrastructure;

import shop.soolsool.payment.ui.dto.response.PayApproveResponse;
import shop.soolsool.payment.ui.dto.response.PayReadyResponse;
import shop.soolsool.receipt.domain.model.Receipt;

public interface PayClient {

    PayReadyResponse ready(final Receipt receipt);

    PayApproveResponse payApprove(final Object... args);
}

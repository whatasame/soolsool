package shop.soolsool.payment.infrastructure.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import shop.soolsool.payment.ui.dto.response.PayReadyResponse;

// TODO : final
@Getter
@JsonNaming(SnakeCaseStrategy.class)
public class KakaoPayReadyResponse {

    private String tid;
    private String nextRedirectPcUrl;

    public PayReadyResponse toReadyResponse() {
        return new PayReadyResponse(tid, nextRedirectPcUrl, null, null);
    }
}

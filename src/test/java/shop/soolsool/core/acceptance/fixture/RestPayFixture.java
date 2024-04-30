package shop.soolsool.acceptance.fixture;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import shop.soolsool.core.common.ApiResponse;
import shop.soolsool.payment.ui.dto.request.PayOrderRequest;
import shop.soolsool.payment.ui.dto.response.PayReadyResponse;
import shop.soolsool.payment.ui.dto.response.PaySuccessResponse;

public abstract class RestPayFixture extends RestFixture {

    public static void 결제_준비(final String accessToken, final Long receiptId) {
        final PayOrderRequest request = new PayOrderRequest(receiptId);

        RestAssured.given()
                .log()
                .all()
                .header(AUTHORIZATION, BEARER + accessToken)
                .contentType(APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/pay/ready")
                .then()
                .extract()
                .body()
                .as(new TypeRef<ApiResponse<PayReadyResponse>>() {});
    }

    public static Long 결제_성공(final String accessToken, final Long receiptId) {
        return RestAssured.given()
                .log()
                .all()
                .header(AUTHORIZATION, BEARER + accessToken)
                .contentType(APPLICATION_JSON_VALUE)
                .param("pg_token", "pgpgpgpg")
                .when()
                .get("/api/pay/success/{receiptId}", receiptId)
                .then()
                .extract()
                .body()
                .as(new TypeRef<ApiResponse<PaySuccessResponse>>() {})
                .getData()
                .getOrderId();
    }
}

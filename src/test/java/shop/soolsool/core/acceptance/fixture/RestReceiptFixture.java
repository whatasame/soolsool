package shop.soolsool.acceptance.fixture;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.restassured.RestAssured;
import shop.soolsool.receipt.ui.response.ReceiptDetailResponse;

public abstract class RestReceiptFixture extends RestFixture {

    public static Long 주문서_생성(final String 토큰) {
        return Long.valueOf(RestAssured.given()
                .log()
                .all()
                .contentType(APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, BEARER + 토큰)
                .when()
                .post("/api/receipts")
                .then()
                .log()
                .all()
                .extract()
                .header("Location")
                .split("/")[2]);
    }

    public static ReceiptDetailResponse 주문서_조회(final String 토큰, final Long 주문서) {
        return RestAssured.given()
                .log()
                .all()
                .contentType(APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, BEARER + 토큰)
                .when()
                .get("/api/receipts/{receiptId}", 주문서)
                .then()
                .log()
                .all()
                .extract()
                .jsonPath()
                .getObject("data", ReceiptDetailResponse.class);
    }
}

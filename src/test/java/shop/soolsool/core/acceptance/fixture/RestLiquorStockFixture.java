package shop.soolsool.acceptance.fixture;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.restassured.RestAssured;
import java.time.LocalDateTime;
import org.springframework.http.HttpHeaders;
import shop.soolsool.liquor.ui.dto.LiquorStockSaveRequest;

public abstract class RestLiquorStockFixture extends RestFixture {

    public static void 술_재고_등록(final String accessToken, final Long liquorId, final int quantity) {
        final LiquorStockSaveRequest request = new LiquorStockSaveRequest(
                liquorId, quantity, LocalDateTime.now().plusYears(1L));

        RestAssured.given()
                .log()
                .all()
                .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .put("/api/liquor-stocks")
                .then()
                .log()
                .all()
                .extract();
    }
}

package shop.soolsool.acceptance.fixture;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.restassured.RestAssured;
import java.util.List;
import shop.soolsool.liquor.ui.dto.LiquorClickAddRequest;
import shop.soolsool.liquor.ui.dto.LiquorImpressionAddRequest;

public abstract class RestLiquorCtrFixture extends RestFixture {

    public static void 술_노출수_증가(final List<Long> liquorIds) {
        final LiquorImpressionAddRequest request = new LiquorImpressionAddRequest(liquorIds);

        RestAssured.given()
                .log()
                .all()
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .patch("/api/liquor-ctr/impressions")
                .then()
                .log()
                .all()
                .extract();
    }

    public static void 술_클릭수_증가(final Long liquorId) {
        final LiquorClickAddRequest request = new LiquorClickAddRequest(liquorId);

        RestAssured.given()
                .log()
                .all()
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .patch("/api/liquor-ctr/clicks")
                .then()
                .log()
                .all()
                .extract();
    }
}

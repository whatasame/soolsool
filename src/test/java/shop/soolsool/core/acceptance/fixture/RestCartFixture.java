package shop.soolsool.acceptance.fixture;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import java.util.List;
import shop.soolsool.cart.ui.dto.request.CartItemSaveRequest;
import shop.soolsool.cart.ui.dto.response.CartItemResponse;

public abstract class RestCartFixture extends RestFixture {

    public static Long 장바구니_상품_추가(final String 토큰, final Long 상품_Id, final Integer 상품_개수) {
        final CartItemSaveRequest cartItemSaveRequest = new CartItemSaveRequest(상품_Id, 상품_개수);

        return RestAssured.given()
                .log()
                .all()
                .contentType(APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, BEARER + 토큰)
                .body(cartItemSaveRequest)
                .when()
                .post("/api/cart-items")
                .then()
                .log()
                .all()
                .extract()
                .jsonPath()
                .getObject("data", Long.class);
    }

    public static List<CartItemResponse> 장바구니_모두_조회(final String 토큰) {
        return RestAssured.given()
                .log()
                .all()
                .contentType(APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, BEARER + 토큰)
                .when()
                .get("/api/cart-items/")
                .then()
                .log()
                .all()
                .extract()
                .jsonPath()
                .getObject("data", new TypeRef<>() {});
    }
}

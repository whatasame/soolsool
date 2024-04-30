package shop.soolsool.cart.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shop.soolsool.acceptance.AcceptanceTest;
import shop.soolsool.acceptance.fixture.RestAuthFixture;
import shop.soolsool.acceptance.fixture.RestCartFixture;
import shop.soolsool.acceptance.fixture.RestLiquorFixture;
import shop.soolsool.acceptance.fixture.RestMemberFixture;
import shop.soolsool.cart.ui.dto.request.CartItemModifyRequest;
import shop.soolsool.cart.ui.dto.request.CartItemSaveRequest;
import shop.soolsool.cart.ui.dto.response.CartItemResponse;

@DisplayName("인수 테스트: /cart-items")
class CartControllerTest extends AcceptanceTest {

    Long 새로_Id, 하이트_Id, 얼음딸기주_Id;

    @BeforeEach
    void setUpData() {
        RestMemberFixture.회원가입_김배달_구매자();
        RestMemberFixture.회원가입_최민족_판매자();

        final String 최민족_토큰 = RestAuthFixture.로그인_최민족_판매자();
        새로_Id = RestLiquorFixture.술_등록_새로_판매중(최민족_토큰);
        하이트_Id = RestLiquorFixture.술_등록_ETC_경기도_하이트_진로_판매중지(최민족_토큰);
        얼음딸기주_Id = RestLiquorFixture.술_등록_과일주_전라북도_얼음딸기주_우영미_판매중(최민족_토큰);
    }

    @Test
    @DisplayName("성공 : 장바구니에 술 추가")
    void createCartItem() {
        // given
        final String 김배달_토큰 = RestAuthFixture.로그인_김배달_구매자();
        final CartItemSaveRequest cartItemSaveRequest = new CartItemSaveRequest(새로_Id, 1);

        // when
        final ExtractableResponse<Response> response = RestAssured.given()
                .log()
                .all()
                .contentType(APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, BEARER + 김배달_토큰)
                .body(cartItemSaveRequest)
                .when()
                .post("/api/cart-items")
                .then()
                .log()
                .all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
    }

    @Test
    @DisplayName("성공 : 장바구니 상품 전체 조회 ")
    void CartItemList() {
        // given
        final String 김배달_토큰 = RestAuthFixture.로그인_김배달_구매자();
        RestCartFixture.장바구니_상품_추가(김배달_토큰, 새로_Id, 2);
        RestCartFixture.장바구니_상품_추가(김배달_토큰, 얼음딸기주_Id, 5);

        // when
        final ExtractableResponse<Response> listResponse = RestAssured.given()
                .log()
                .all()
                .contentType(APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, BEARER + 김배달_토큰)
                .when()
                .get("/api/cart-items/")
                .then()
                .log()
                .all()
                .extract();

        // then
        assertThat(listResponse.statusCode()).isEqualTo(OK.value());
        assertThat(listResponse.jsonPath().getObject("data", new TypeRef<List<CartItemResponse>>() {}))
                .hasSize(2);
    }

    @Test
    @DisplayName("성공 : 장바구니 상품 수량 변경")
    void modifyCartItemCount() {
        // given
        final String 김배달_토큰 = RestAuthFixture.로그인_김배달_구매자();
        final Long 장바구니_새로_Id = RestCartFixture.장바구니_상품_추가(김배달_토큰, 새로_Id, 2);

        final CartItemModifyRequest modifyRequest = new CartItemModifyRequest(3);

        // when
        final ExtractableResponse<Response> modifyResponse = RestAssured.given()
                .log()
                .all()
                .contentType(APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, BEARER + 김배달_토큰)
                .body(modifyRequest)
                .when()
                .patch("/api/cart-items/{cartItemId}", 장바구니_새로_Id)
                .then()
                .log()
                .all()
                .extract();

        // then
        assertThat(modifyResponse.statusCode()).isEqualTo(OK.value());
    }

    @Test
    @DisplayName("성공 : 장바구니 상품 삭제")
    void removeCartItem() {
        // given
        final String 김배달_토큰 = RestAuthFixture.로그인_김배달_구매자();
        final Long 장바구니_새로_Id = RestCartFixture.장바구니_상품_추가(김배달_토큰, 새로_Id, 2);

        // when
        final ExtractableResponse<Response> removeResponse = RestAssured.given()
                .log()
                .all()
                .contentType(APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, BEARER + 김배달_토큰)
                .when()
                .delete("/api/cart-items/{cartItemId}", 장바구니_새로_Id)
                .then()
                .log()
                .all()
                .extract();

        // then
        assertThat(removeResponse.statusCode()).isEqualTo(OK.value());
        assertThat(RestCartFixture.장바구니_모두_조회(김배달_토큰)).isEmpty();
    }

    @Test
    @DisplayName("성공 : 장바구니 상품 모두 삭제 ")
    void removeCartItemList() {
        // given
        final String 김배달_토큰 = RestAuthFixture.로그인_김배달_구매자();
        RestCartFixture.장바구니_상품_추가(김배달_토큰, 새로_Id, 2);
        RestCartFixture.장바구니_상품_추가(김배달_토큰, 얼음딸기주_Id, 5);

        // when
        final ExtractableResponse<Response> listResponse = RestAssured.given()
                .log()
                .all()
                .contentType(APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, BEARER + 김배달_토큰)
                .when()
                .delete("/api/cart-items/")
                .then()
                .log()
                .all()
                .extract();

        // then
        assertThat(listResponse.statusCode()).isEqualTo(OK.value());
        assertThat(RestCartFixture.장바구니_모두_조회(김배달_토큰)).isEmpty();
    }
}

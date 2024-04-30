package shop.soolsool.liquor.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import shop.soolsool.acceptance.AcceptanceTest;
import shop.soolsool.acceptance.fixture.RestAuthFixture;
import shop.soolsool.acceptance.fixture.RestLiquorFixture;
import shop.soolsool.acceptance.fixture.RestMemberFixture;
import shop.soolsool.liquor.ui.dto.LiquorStockSaveRequest;

@DisplayName("인수 테스트: /liquor-stocks")
class LiquorStockAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    void setUpData() {
        RestMemberFixture.회원가입_최민족_판매자();
    }

    @Test
    @DisplayName("술 재고를 추가한다.")
    void addLiquorStock() {
        // given
        final String accessToken = RestAuthFixture.로그인_최민족_판매자();
        final Long 새로 = RestLiquorFixture.술_등록_새로_판매중(accessToken);

        final LiquorStockSaveRequest request =
                new LiquorStockSaveRequest(새로, 100, LocalDateTime.now().plusYears(1L));

        // when
        final ExtractableResponse<Response> response = RestAssured.given()
                .log()
                .all()
                .header("Authorization", BEARER + accessToken)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .put("/api/liquor-stocks")
                .then()
                .log()
                .all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}

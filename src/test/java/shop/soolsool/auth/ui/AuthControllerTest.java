package shop.soolsool.auth.ui;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shop.soolsool.acceptance.AcceptanceTest;
import shop.soolsool.acceptance.fixture.RestMemberFixture;
import shop.soolsool.auth.ui.dto.LoginRequest;
import shop.soolsool.auth.ui.dto.LoginResponse;
import shop.soolsool.core.common.ApiResponse;

@DisplayName("인수 테스트: /auth")
class AuthControllerTest extends AcceptanceTest {

    @BeforeEach
    void setUpData() {
        RestMemberFixture.회원가입_김배달_구매자();
    }

    @Test
    @DisplayName("로그인할 때 회원가 일치할 시, jwt 토큰을 발급 한다.")
    void loginSuccessTest() {
        // given
        final String email = "kim@email.com";
        final String password = "baedal";
        final LoginRequest loginRequest = new LoginRequest(email, password);

        // when
        final ExtractableResponse<Response> response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(loginRequest)
                .log()
                .all()
                .when()
                .post("/api/auth/login")
                .then()
                .log()
                .all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body()
                        .as(new TypeRef<ApiResponse<LoginResponse>>() {})
                        .getData()
                        .getAccessToken())
                .isNotNull();
    }
}

package shop.soolsool.payment.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shop.soolsool.acceptance.AcceptanceTest;
import shop.soolsool.acceptance.fixture.RestAuthFixture;
import shop.soolsool.acceptance.fixture.RestCartFixture;
import shop.soolsool.acceptance.fixture.RestLiquorFixture;
import shop.soolsool.acceptance.fixture.RestLiquorStockFixture;
import shop.soolsool.acceptance.fixture.RestMemberFixture;
import shop.soolsool.acceptance.fixture.RestPayFixture;
import shop.soolsool.acceptance.fixture.RestReceiptFixture;
import shop.soolsool.core.common.ApiResponse;
import shop.soolsool.member.ui.dto.MemberDetailResponse;
import shop.soolsool.payment.ui.dto.request.PayOrderRequest;
import shop.soolsool.payment.ui.dto.response.PayReadyResponse;
import shop.soolsool.payment.ui.dto.response.PaySuccessResponse;
import shop.soolsool.receipt.ui.response.ReceiptDetailResponse;

@DisplayName("인수 테스트: /pay")
class PaymentControllerTest extends AcceptanceTest {

    @BeforeEach
    void setUpData() {
        RestMemberFixture.회원가입_김배달_구매자();
        RestMemberFixture.회원가입_최민족_판매자();

        final String 최민족_토큰 = RestAuthFixture.로그인_최민족_판매자();
        final Long 새로 = RestLiquorFixture.술_등록_새로_판매중(최민족_토큰);
        final Long 하이트 = RestLiquorFixture.술_등록_ETC_경기도_하이트_진로_판매중지(최민족_토큰);
        final Long 얼음딸기주 = RestLiquorFixture.술_등록_과일주_전라북도_얼음딸기주_우영미_판매중(최민족_토큰);
        RestLiquorStockFixture.술_재고_등록(최민족_토큰, 새로, 100);
        RestLiquorStockFixture.술_재고_등록(최민족_토큰, 하이트, 200);
        RestLiquorStockFixture.술_재고_등록(최민족_토큰, 얼음딸기주, 300);

        final String 김배달_토큰 = RestAuthFixture.로그인_김배달_구매자();
        RestCartFixture.장바구니_상품_추가(김배달_토큰, 새로, 1);
        RestCartFixture.장바구니_상품_추가(김배달_토큰, 얼음딸기주, 3);
    }

    @Test
    @DisplayName("주문서로 결제를 준비한다.")
    void ready() {
        /* given */
        final String 김배달_토큰 = RestAuthFixture.로그인_김배달_구매자();
        final Long 김배달_주문서 = RestReceiptFixture.주문서_생성(김배달_토큰);

        final PayOrderRequest request = new PayOrderRequest(김배달_주문서);

        /* when */
        final PayReadyResponse data = RestAssured.given()
                .log()
                .all()
                .header(AUTHORIZATION, BEARER + 김배달_토큰)
                .contentType(APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/pay/ready")
                .then()
                .extract()
                .body()
                .as(new TypeRef<ApiResponse<PayReadyResponse>>() {})
                .getData();

        /* then */
        assertThat(data.getTid()).isEqualTo("1");
        assertThat(data.getNextRedirectPcUrl()).isEqualTo("http://pc-url");
        assertThat(data.getNextRedirectMobileUrl()).isEqualTo("http://mobile-url");
        assertThat(data.getNextRedirectAppUrl()).isEqualTo("http://app-url");
    }

    @Test
    @DisplayName("결제 성공을 알린다.")
    void success() {
        /* given */
        final String 김배달_토큰 = RestAuthFixture.로그인_김배달_구매자();
        final Long 김배달_주문서 = RestReceiptFixture.주문서_생성(김배달_토큰);
        RestPayFixture.결제_준비(김배달_토큰, 김배달_주문서);

        /* when */
        final PaySuccessResponse response = RestAssured.given()
                .log()
                .all()
                .header(AUTHORIZATION, BEARER + 김배달_토큰)
                .contentType(APPLICATION_JSON_VALUE)
                .param("pg_token", "pgpgpgpg")
                .when()
                .get("/api/pay/success/{receiptId}", 김배달_주문서)
                .then()
                .extract()
                .body()
                .as(new TypeRef<ApiResponse<PaySuccessResponse>>() {})
                .getData();

        /* then */
        assertThat(response.getOrderId()).isNotNull();

        final MemberDetailResponse memberDetailResponse = RestMemberFixture.회원_정보_조회(김배달_토큰);
        assertThat(memberDetailResponse.getMileage()).isEqualTo("9000");
    }

    @Test
    @DisplayName("결제를 취소한다.")
    void cancel() {
        /* given */
        final String 김배달_토큰 = RestAuthFixture.로그인_김배달_구매자();
        final Long 김배달_주문서 = RestReceiptFixture.주문서_생성(김배달_토큰);
        RestPayFixture.결제_준비(김배달_토큰, 김배달_주문서);

        /* when */
        RestAssured.given()
                .log()
                .all()
                .header(AUTHORIZATION, BEARER + 김배달_토큰)
                .contentType(APPLICATION_JSON_VALUE)
                .when()
                .get("/api/pay/cancel/{receiptId}", 김배달_주문서)
                .then();

        /* then */
        final ReceiptDetailResponse 주문서_조회 = RestReceiptFixture.주문서_조회(김배달_토큰, 김배달_주문서);

        assertThat(주문서_조회.getReceiptStatus()).isEqualTo("CANCELED");
    }

    @Test
    @DisplayName("결제를 실패한다.")
    void fail() {
        /* given */
        final String 김배달_토큰 = RestAuthFixture.로그인_김배달_구매자();
        final Long 김배달_주문서 = RestReceiptFixture.주문서_생성(김배달_토큰);
        RestPayFixture.결제_준비(김배달_토큰, 김배달_주문서);

        /* when */
        RestAssured.given()
                .log()
                .all()
                .header(AUTHORIZATION, BEARER + 김배달_토큰)
                .contentType(APPLICATION_JSON_VALUE)
                .when()
                .get("/api/pay/fail/{receiptId}", 김배달_주문서)
                .then();

        /* then */
        final ReceiptDetailResponse 주문서_조회 = RestReceiptFixture.주문서_조회(김배달_토큰, 김배달_주문서);

        assertThat(주문서_조회.getReceiptStatus()).isEqualTo("CANCELED");
    }
}

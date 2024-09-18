package shop.soolsool.auth.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.mock.web.MockHttpServletRequest;

@DisplayName("단위 테스트: AuthorizationExtractor")
class AuthorizationExtractorTest {

    private AuthorizationExtractor authorizationExtractor;

    @BeforeEach
    public void init() {
        authorizationExtractor = new AuthorizationExtractor();
    }

    @Test
    @DisplayName("토큰을 정상적으로 추출한다.")
    void extractTokenSuccessTest() {
        /* given */
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer 123");

        /* when */
        final String extract = authorizationExtractor.extractToken(request);

        /* then */
        assertThat(extract).isEqualTo("123");
    }

    @Test
    @DisplayName("토큰 헤더가 존재하지 않을때, token은 empty이다")
    void extractTokenFailWithDoesNotExistHeader() {
        /* given */
        final MockHttpServletRequest request = new MockHttpServletRequest();

        /* when & then */
        final String token = authorizationExtractor.extractToken(request);
        assertThat(token).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    @DisplayName("토큰 값이 존재하지 않으면 token은 empty이다")
    void extractTokenFailWithDoesNotExistValue(final String value) {
        /* given */
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HttpHeaders.AUTHORIZATION, value);

        /* when & then */
        final String token = authorizationExtractor.extractToken(request);
        assertThat(token).isEmpty();
    }

    @Test
    @DisplayName("토큰 값이 Bearer로 시작하지 않으면 token은 empty이다")
    void extractTokenFailWithDoesNotStartWithBearer() {
        /* given */
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bear 123");

        /* when & then */
        final String token = authorizationExtractor.extractToken(request);
        assertThat(token).isEmpty();
    }
}
package shop.soolsool.auth.application;

import static org.assertj.core.api.Assertions.assertThatCode;
import static shop.soolsool.member.code.MemberErrorCode.MEMBER_NO_INFORMATION;
import static shop.soolsool.member.code.MemberErrorCode.MEMBER_NO_MATCH_PASSWORD;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import shop.soolsool.auth.domain.TokenProvider;
import shop.soolsool.auth.ui.dto.LoginRequest;
import shop.soolsool.core.exception.SoolSoolException;

@DataJpaTest
@Import({AuthService.class, TokenProvider.class})
@DisplayName("통합 테스트: AuthService")
class AuthServiceIntegrationTest {

    @Autowired
    private AuthService authService;

    @Test
    @Sql({"/member-type.sql", "/member.sql"})
    @DisplayName("이메일과 비밀번호가 일치하면 token을 발급한다.")
    void createTokenWithEmailAndPassword() {
        // given
        final LoginRequest loginRequest = new LoginRequest("kim@email.com", "baedal");

        // when & then
        assertThatCode(() -> authService.createToken(loginRequest)).doesNotThrowAnyException();
    }

    @Test
    @Sql({"/member-type.sql", "/member.sql"})
    @DisplayName("이메일이 일치하지 않으면 토큰 발급에 실패한다")
    void throwErrorWithWrongEmail() {
        // given
        final LoginRequest loginRequest = new LoginRequest("wrong@email.com", "baedal");

        // when & then
        assertThatCode(() -> authService.createToken(loginRequest))
                .isInstanceOf(SoolSoolException.class)
                .hasMessage(MEMBER_NO_INFORMATION.getMessage());
    }

    @Test
    @Sql({"/member-type.sql", "/member.sql"})
    @DisplayName("비밀번호가 일치하지 않으면 토큰 발급에 실패한다")
    void throwErrorWithWrongPassword() {
        // given
        final LoginRequest loginRequest = new LoginRequest("kim@email.com", "wrong");

        // when & then
        assertThatCode(() -> authService.createToken(loginRequest))
                .isInstanceOf(SoolSoolException.class)
                .hasMessage(MEMBER_NO_MATCH_PASSWORD.getMessage());
    }
}

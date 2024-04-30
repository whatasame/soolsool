package shop.soolsool.member.domain.model;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shop.soolsool.member.domain.model.vo.MemberRoleType;

@DisplayName("단위 테스트: MemberRoleType")
class MemberRoleTest {

    @Test
    @DisplayName("회원 역할을 정상적으로 생성한다.")
    void create() {
        /* given */
        MemberRoleType type = MemberRoleType.CUSTOMER;

        /* when & then */
        assertThatCode(() -> new MemberRole(type)).doesNotThrowAnyException();
    }
}

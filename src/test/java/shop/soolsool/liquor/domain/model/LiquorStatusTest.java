package shop.soolsool.liquor.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shop.soolsool.liquor.domain.model.vo.LiquorStatusType;

@DisplayName("단위 테스트: LiquorStatus")
class LiquorStatusTest {

    @Test
    @DisplayName("술 상태를 정상적으로 생성한다.")
    void create() {
        /* given */
        final LiquorStatusType type = LiquorStatusType.ON_SALE;

        /* when */
        final LiquorStatus status = new LiquorStatus(type);

        /* then */
        assertThat(status.getType()).isEqualTo(type);
    }
}

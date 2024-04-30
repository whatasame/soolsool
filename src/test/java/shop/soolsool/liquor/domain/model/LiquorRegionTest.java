package shop.soolsool.liquor.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shop.soolsool.liquor.domain.model.vo.LiquorRegionType;

@DisplayName("단위 테스트: LiquorRegion")
class LiquorRegionTest {

    @Test
    @DisplayName("술 단위를 정상적으로 생성한다.")
    void create() {
        /* given */
        final LiquorRegionType type = LiquorRegionType.GYEONGGI_DO;

        /* when */
        final LiquorRegion liquorRegion = new LiquorRegion(type);

        /* then */
        assertThat(liquorRegion.getType()).isEqualTo(type);
    }
}

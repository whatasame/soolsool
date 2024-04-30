package shop.soolsool.liquor.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static shop.soolsool.liquor.domain.model.vo.LiquorRegionType.GYEONGGI_DO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import shop.soolsool.core.exception.SoolSoolException;
import shop.soolsool.liquor.code.LiquorErrorCode;
import shop.soolsool.liquor.domain.model.LiquorRegion;

@DataJpaTest
@Sql("/liquor-type.sql")
@DisplayName("통합 테스트: LiquorRegionRepository")
class LiquorRegionRepositoryTest {

    @Autowired
    private LiquorRegionRepository liquorRegionRepository;

    @Test
    @DisplayName("LiquorRegionStatus의 name를 가지고 LiquorRegion을 조회한다.")
    void findByLiquorRegionType_type() {
        // given
        final LiquorRegion 경기도 = liquorRegionRepository
                .findByType(GYEONGGI_DO)
                .orElseThrow(() -> new SoolSoolException(LiquorErrorCode.NOT_LIQUOR_REGION_FOUND));

        // when & then
        assertThat(경기도.getType().getName()).isEqualTo(GYEONGGI_DO.getName());
    }
}

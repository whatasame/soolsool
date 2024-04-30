package shop.soolsool.liquor.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import shop.soolsool.liquor.ui.dto.LiquorStockSaveRequest;

@DataJpaTest
@Import(LiquorStockService.class)
@DisplayName("통합테스트: LiquorStockService")
class LiquorStockServiceIntegrationTest {

    @Autowired
    LiquorStockService liquorStockService;

    @Test
    @Sql({"/member-type.sql", "/member.sql", "/liquor-type.sql", "/liquor.sql"})
    @DisplayName("술 재고를 정상적으로 저장한다.")
    void saveLiquorStock() {
        /* given */
        final Long 새로 = 1L;

        final LiquorStockSaveRequest request =
                new LiquorStockSaveRequest(새로, 100, LocalDateTime.now().plusYears(1L));

        /* when */
        final Long 재고 = liquorStockService.saveLiquorStock(request);

        /* then */
        assertThat(재고).isNotNull();
    }
}

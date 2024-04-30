package shop.soolsool.liquor.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import shop.soolsool.core.config.RedisTestConfig;
import shop.soolsool.liquor.domain.repository.LiquorCtrRedisRepository;
import shop.soolsool.liquor.infrastructure.RedisLiquorCtr;
import shop.soolsool.liquor.ui.dto.LiquorClickAddRequest;
import shop.soolsool.liquor.ui.dto.LiquorImpressionAddRequest;

@DataJpaTest
@Import({LiquorCtrService.class, RedisTestConfig.class, LiquorCtrRedisRepository.class})
@DisplayName("통합 테스트: LiquorCtrService")
class LiquorCtrServiceIntegrationTest {

    private static final String LIQUOR_CTR_KEY = "LIQUOR_CTR";

    @Autowired
    LiquorCtrService liquorCtrService;

    @Autowired
    RedissonClient redissonClient;

    @BeforeEach
    @AfterEach
    void setRedisLiquorCtr() {
        redissonClient.getMapCache(LIQUOR_CTR_KEY).clear();
    }

    @Test
    @Sql({"/liquor-type.sql", "/liquor.sql", "/liquor-ctr.sql"})
    @DisplayName("클릭율을 조회한다.")
    void getLiquorCtrByByLiquorId() {
        // given
        final long liquorId = 1L;

        final long impression = 1L;
        final long click = 1L;
        redissonClient.getMapCache(LIQUOR_CTR_KEY).put(liquorId, new RedisLiquorCtr(impression, click));

        // when
        final double ctr = liquorCtrService.getLiquorCtrByLiquorId(liquorId);

        // then
        assertThat(ctr).isEqualTo(getExpectedCtr(impression, click));
    }

    @Test
    @Sql({"/liquor-type.sql", "/liquor.sql", "/liquor-ctr.sql"})
    @DisplayName("노출수를 증가시킨다.")
    void increaseImpression() {
        // given
        final long liquorId = 1L;
        final LiquorImpressionAddRequest request = new LiquorImpressionAddRequest(List.of(liquorId));

        final long impression = 1L;
        final long click = 1L;
        redissonClient.getMapCache(LIQUOR_CTR_KEY).put(liquorId, new RedisLiquorCtr(impression, click));

        // when
        liquorCtrService.increaseImpression(request);

        // then
        final double ctr = liquorCtrService.getLiquorCtrByLiquorId(liquorId);
        assertThat(ctr).isEqualTo(getExpectedCtr(impression + 1, click));
    }

    @Test
    @Sql({"/liquor-type.sql", "/liquor.sql", "/liquor-ctr.sql"})
    @DisplayName("클릭수를 증가시킨다.")
    void increaseClick() {
        // given
        final long liquorId = 1L;
        final LiquorClickAddRequest request = new LiquorClickAddRequest(liquorId);

        final long impression = 2L;
        final long click = 1L;
        redissonClient.getMapCache(LIQUOR_CTR_KEY).put(liquorId, new RedisLiquorCtr(impression, click));

        // when
        liquorCtrService.increaseClick(request);

        // then
        final double ctr = liquorCtrService.getLiquorCtrByLiquorId(liquorId);
        assertThat(ctr).isEqualTo(getExpectedCtr(impression, click + 1));
    }

    private double getExpectedCtr(final long impression, final long click) {
        final double ratio = (double) click / impression;

        return Math.round(ratio * 100) / 100.0;
    }
}

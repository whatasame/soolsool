package shop.soolsool.statistics.domain.model;

import static org.assertj.core.api.Assertions.assertThatNoException;

import java.math.BigInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shop.soolsool.statistics.domain.model.vo.BrewType;
import shop.soolsool.statistics.domain.model.vo.Click;
import shop.soolsool.statistics.domain.model.vo.Impression;
import shop.soolsool.statistics.domain.model.vo.Region;
import shop.soolsool.statistics.domain.model.vo.SalePrice;
import shop.soolsool.statistics.domain.model.vo.SaleQuantity;

@DisplayName("단위 테스트: Statistic")
class StatisticTest {

    @Test
    @DisplayName("객체 생성 성공")
    void create() {
        // when & then
        assertThatNoException().isThrownBy(() -> Statistic.builder()
                .salePrice(new SalePrice(new BigInteger("10000")))
                .saleQuantity(new SaleQuantity(new BigInteger("100")))
                .impression(new Impression(new BigInteger("1000")))
                .region(new Region("서울"))
                .brewType(new BrewType("과일주"))
                .click(new Click(new BigInteger("120")))
                .build());
    }
}

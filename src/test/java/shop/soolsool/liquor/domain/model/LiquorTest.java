package shop.soolsool.liquor.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shop.soolsool.liquor.domain.model.vo.LiquorBrewType;
import shop.soolsool.liquor.domain.model.vo.LiquorRegionType;
import shop.soolsool.liquor.domain.model.vo.LiquorStatusType;

@DisplayName("단위 테스트: Liquor")
class LiquorTest {

    @Test
    @DisplayName("술을 정상적으로 생성한다.")
    void create() {
        /* given */
        final LiquorBrew brew = new LiquorBrew(LiquorBrewType.SOJU);
        final LiquorRegion region = new LiquorRegion(LiquorRegionType.GYEONGGI_DO);
        final LiquorStatus status = new LiquorStatus(LiquorStatusType.ON_SALE);
        final String name = "마싯는 소주";
        final String price = "10000";
        final String brand = "우아한";
        final String imageUrl = "soju.png";
        final int stock = 77;
        final double alcohol = 17.2;
        final int volume = 500;

        /* when */
        final Liquor liquor = Liquor.builder()
                .brew(brew)
                .region(region)
                .status(status)
                .name(name)
                .price(price)
                .brand(brand)
                .imageUrl(imageUrl)
                .alcohol(alcohol)
                .volume(volume)
                .build();

        /* then */
        assertAll(
                () -> assertThat(liquor.getBrew()).isEqualTo(brew),
                () -> assertThat(liquor.getRegion()).isEqualTo(region),
                () -> assertThat(liquor.getStatus()).isEqualTo(status),
                () -> assertThat(liquor.getName()).isEqualTo(name),
                () -> assertThat(liquor.getPrice()).isEqualTo(price),
                () -> assertThat(liquor.getBrand()).isEqualTo(brand),
                () -> assertThat(liquor.getImageUrl()).isEqualTo(imageUrl),
                () -> assertThat(liquor.getAlcohol()).isEqualTo(alcohol),
                () -> assertThat(liquor.getVolume()).isEqualTo(volume));
    }
}

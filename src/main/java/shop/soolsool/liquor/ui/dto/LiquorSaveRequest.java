package shop.soolsool.liquor.ui.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import shop.soolsool.liquor.domain.model.Liquor;
import shop.soolsool.liquor.domain.model.LiquorBrew;
import shop.soolsool.liquor.domain.model.LiquorRegion;
import shop.soolsool.liquor.domain.model.LiquorStatus;

@Getter
@ToString
@RequiredArgsConstructor
public class LiquorSaveRequest {

    private final String brew;
    private final String region;
    private final String status;
    private final String name;
    private final String price;
    private final String brand;
    private final String imageUrl;
    private final Double alcohol;
    private final Integer volume;

    public Liquor toEntity(final LiquorBrew brew, final LiquorRegion region, final LiquorStatus status) {

        return new Liquor(brew, region, status, name, price, brand, imageUrl, alcohol, volume);
    }
}

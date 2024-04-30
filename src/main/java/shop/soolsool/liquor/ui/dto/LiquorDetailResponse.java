package shop.soolsool.liquor.ui.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shop.soolsool.liquor.domain.model.Liquor;

@Getter
@RequiredArgsConstructor
public class LiquorDetailResponse {

    private final Long id;
    private final String name;
    private final String price;
    private final String brand;
    private final String imageUrl;
    private final Integer stock;
    private final Double alcohol;
    private final Integer volume;

    public static LiquorDetailResponse of(final Liquor liquor) {
        return new LiquorDetailResponse(
                liquor.getId(),
                liquor.getName(),
                liquor.getPrice().toString(),
                liquor.getBrand(),
                liquor.getImageUrl(),
                liquor.getTotalStock(),
                liquor.getAlcohol(),
                liquor.getVolume());
    }
}

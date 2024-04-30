package shop.soolsool.liquor.ui.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shop.soolsool.liquor.domain.model.Liquor;
import shop.soolsool.liquor.domain.model.vo.LiquorCtrClick;

@Getter
@RequiredArgsConstructor
public class LiquorClickElementDto {

    private final Long id;
    private final String name;
    private final String price;
    private final String imageUrl;
    private final Integer stock;
    private final Long clickCount;

    public LiquorClickElementDto(final Liquor liquor, final LiquorCtrClick clickCount) {
        this(
                liquor.getId(),
                liquor.getName(),
                liquor.getPrice().toString(),
                liquor.getImageUrl(),
                liquor.getTotalStock(),
                clickCount.getCount());
    }
}

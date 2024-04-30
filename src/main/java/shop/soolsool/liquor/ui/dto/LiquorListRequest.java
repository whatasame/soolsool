package shop.soolsool.liquor.ui.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import shop.soolsool.liquor.domain.model.vo.LiquorBrewType;
import shop.soolsool.liquor.domain.model.vo.LiquorRegionType;
import shop.soolsool.liquor.domain.model.vo.LiquorStatusType;

@Getter
@RequiredArgsConstructor
public class LiquorListRequest {

    @Nullable
    private final LiquorBrewType brew;

    @Nullable
    private final LiquorRegionType region;

    @Nullable
    private final LiquorStatusType status;

    @Nullable
    private final String brand;

    @Nullable
    private final Long liquorId;

    @Nullable
    private final Long clickCount;
}

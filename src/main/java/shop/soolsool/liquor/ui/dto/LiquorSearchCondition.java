package shop.soolsool.liquor.ui.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shop.soolsool.liquor.domain.model.LiquorBrew;
import shop.soolsool.liquor.domain.model.LiquorRegion;
import shop.soolsool.liquor.domain.model.LiquorStatus;

@Getter
@RequiredArgsConstructor
public class LiquorSearchCondition {

    private final LiquorRegion liquorRegion;
    private final LiquorBrew liquorBrew;
    private final LiquorStatus liquorStatus;
    private final String brand;
}

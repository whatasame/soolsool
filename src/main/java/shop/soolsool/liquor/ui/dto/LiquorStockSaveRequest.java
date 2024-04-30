package shop.soolsool.liquor.ui.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import shop.soolsool.liquor.domain.model.LiquorStock;
import shop.soolsool.liquor.domain.model.vo.LiquorStockCount;

@Getter
@ToString
@RequiredArgsConstructor
public class LiquorStockSaveRequest {

    private final Long liquorId;
    private final Integer stock;
    private final LocalDateTime expiredAt;

    public LiquorStock toEntity() {
        return LiquorStock.builder()
                .liquorId(liquorId)
                .stock(new LiquorStockCount(stock))
                .expiredAt(expiredAt)
                .build();
    }
}

package shop.soolsool.liquor.infrastructure;

import lombok.RequiredArgsConstructor;
import shop.soolsool.liquor.domain.model.LiquorCtr;

@RequiredArgsConstructor
public class RedisLiquorCtr {

    private final Long impression;
    private final Long click;

    public RedisLiquorCtr increaseImpression() {
        return new RedisLiquorCtr(impression + 1, click);
    }

    public RedisLiquorCtr increaseClick() {
        return new RedisLiquorCtr(impression, click + 1);
    }

    public LiquorCtr toEntity(final Long liquorId) {
        return LiquorCtr.builder()
                .liquorId(liquorId)
                .impression(impression)
                .click(click)
                .build();
    }
}

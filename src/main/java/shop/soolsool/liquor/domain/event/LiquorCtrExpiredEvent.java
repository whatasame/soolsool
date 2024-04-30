package shop.soolsool.liquor.domain.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shop.soolsool.liquor.domain.model.LiquorCtr;
import shop.soolsool.liquor.infrastructure.RedisLiquorCtr;

@Getter
@RequiredArgsConstructor
public class LiquorCtrExpiredEvent {

    private final Long liquorId;
    private final RedisLiquorCtr redisLiquorCtr;

    public LiquorCtr getLiquorCtr() {
        return redisLiquorCtr.toEntity(liquorId);
    }
}

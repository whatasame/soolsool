package shop.soolsool.liquor.domain.repository;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.api.map.event.EntryExpiredListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import shop.soolsool.core.exception.SoolSoolException;
import shop.soolsool.core.infra.LockType;
import shop.soolsool.liquor.code.LiquorCtrErrorCode;
import shop.soolsool.liquor.code.LiquorErrorCode;
import shop.soolsool.liquor.domain.event.LiquorCtrExpiredEvent;
import shop.soolsool.liquor.domain.model.LiquorCtr;
import shop.soolsool.liquor.infrastructure.RedisLiquorCtr;

@Component
@Slf4j
public class LiquorCtrRedisRepository {

    // TODO: ~Repository vs ~Service
    private static final String LIQUOR_CTR_KEY = "LIQUOR_CTR";
    private static final long LOCK_WAIT_TIME = 1L;
    private static final long LOCK_LEASE_TIME = 1L;
    private static final long LIQUOR_CTR_TTL = 5L;

    private final LiquorCtrRepository liquorCtrRepository;

    private final RedissonClient redissonClient;

    private final RMapCache<Long, RedisLiquorCtr> liquorCtrs;

    public LiquorCtrRedisRepository(
            final LiquorCtrRepository liquorCtrRepository,
            final RedissonClient redissonClient,
            final ApplicationEventPublisher publisher) {
        redissonClient.getMapCache(LIQUOR_CTR_KEY).addListener((EntryExpiredListener<Long, RedisLiquorCtr>)
                event -> publisher.publishEvent(new LiquorCtrExpiredEvent(event.getKey(), event.getValue())));

        this.liquorCtrRepository = liquorCtrRepository;
        this.redissonClient = redissonClient;
        liquorCtrs = redissonClient.getMapCache(LIQUOR_CTR_KEY);
    }

    public double getCtr(final Long liquorId) {
        return lookUpLiquorCtr(liquorId).toEntity(liquorId).getCtr();
    }

    public void increaseImpression(final Long liquorId) {
        final RLock liquorCtrLock = getLiquorCtrLock(liquorId);

        try {
            liquorCtrLock.tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS);

            liquorCtrs.replace(liquorId, lookUpLiquorCtr(liquorId).increaseImpression());
        } catch (final InterruptedException e) {
            log.error("노출수 갱신에 실패했습니다. | liquorId : {}", liquorId);

            Thread.currentThread().interrupt();

            throw new SoolSoolException(LiquorErrorCode.INTERRUPTED_THREAD);
        } finally {
            unlock(liquorCtrLock);
        }
    }

    public void increaseClick(final Long liquorId) {
        final RLock liquorCtrLock = getLiquorCtrLock(liquorId);

        try {
            liquorCtrLock.tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS);

            liquorCtrs.replace(liquorId, lookUpLiquorCtr(liquorId).increaseClick());
        } catch (final InterruptedException e) {
            log.error("클릭수 갱신에 실패했습니다. | liquorId : {}", liquorId);

            Thread.currentThread().interrupt();

            throw new SoolSoolException(LiquorErrorCode.INTERRUPTED_THREAD);
        } finally {
            unlock(liquorCtrLock);
        }
    }

    private RLock getLiquorCtrLock(final Long liquorId) {
        return redissonClient.getLock(LockType.LIQUOR_CTR.getPrefix() + liquorId);
    }

    private void unlock(final RLock rLock) {
        if (rLock.isLocked() && rLock.isHeldByCurrentThread()) {
            rLock.unlock();
        }
    }

    // TODO: 만료 테스트는 어떻게 해야할까?
    private RedisLiquorCtr lookUpLiquorCtr(final Long liquorId) {
        if (!liquorCtrs.containsKey(liquorId)) {
            final LiquorCtr liquorCtr = liquorCtrRepository
                    .findByLiquorId(liquorId)
                    .orElseThrow(() -> new SoolSoolException(LiquorCtrErrorCode.NOT_LIQUOR_CTR_FOUND));

            liquorCtrs.put(
                    liquorId,
                    new RedisLiquorCtr(liquorCtr.getImpression(), liquorCtr.getClick()),
                    LIQUOR_CTR_TTL,
                    TimeUnit.MINUTES);
        }

        return liquorCtrs.get(liquorId);
    }
}

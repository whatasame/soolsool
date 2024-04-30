package shop.soolsool.liquor.domain.repository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import shop.soolsool.liquor.domain.model.LiquorStatus;
import shop.soolsool.liquor.domain.model.vo.LiquorStatusType;

@Slf4j
@Component
@RequiredArgsConstructor
public class LiquorStatusCache {

    private final LiquorStatusRepository liquorStatusRepository;

    @Cacheable(
            value = "liquorStatus",
            key = "#type",
            condition = "#type!=null",
            unless = "#result==null",
            cacheManager = "caffeineCacheManager")
    public Optional<LiquorStatus> findByType(final LiquorStatusType type) {
        log.info("LiquorStatusCache {}", type);
        return liquorStatusRepository.findByType(type);
    }
}

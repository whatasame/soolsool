package shop.soolsool.liquor.domain.repository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import shop.soolsool.liquor.domain.model.LiquorRegion;
import shop.soolsool.liquor.domain.model.vo.LiquorRegionType;

@Slf4j
@Component
@RequiredArgsConstructor
public class LiquorRegionCache {

    private final LiquorRegionRepository liquorRegionRepository;

    @Cacheable(
            value = "liquorRegion",
            key = "#type",
            condition = "#type!=null",
            unless = "#result==null",
            cacheManager = "caffeineCacheManager")
    public Optional<LiquorRegion> findByType(final LiquorRegionType type) {
        log.info("LiquorRegionCache {}", type);
        return liquorRegionRepository.findByType(type);
    }
}

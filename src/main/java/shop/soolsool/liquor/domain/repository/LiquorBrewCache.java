package shop.soolsool.liquor.domain.repository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import shop.soolsool.liquor.domain.model.LiquorBrew;
import shop.soolsool.liquor.domain.model.vo.LiquorBrewType;

@Slf4j
@Component
@RequiredArgsConstructor
public class LiquorBrewCache {

    private final LiquorBrewRepository liquorBrewRepository;

    @Cacheable(
            value = "liquorBrew",
            key = "#type",
            condition = "#type!=null",
            unless = "#result==null",
            cacheManager = "caffeineCacheManager")
    public Optional<LiquorBrew> findByType(final LiquorBrewType type) {
        log.info("LiquorBrewCache {}", type);
        return liquorBrewRepository.findByType(type);
    }
}

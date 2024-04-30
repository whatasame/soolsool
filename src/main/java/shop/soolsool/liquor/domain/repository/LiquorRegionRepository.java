package shop.soolsool.liquor.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.soolsool.liquor.domain.model.LiquorRegion;
import shop.soolsool.liquor.domain.model.vo.LiquorRegionType;

@Repository
public interface LiquorRegionRepository extends JpaRepository<LiquorRegion, Long> {

    Optional<LiquorRegion> findByType(final LiquorRegionType type);
}

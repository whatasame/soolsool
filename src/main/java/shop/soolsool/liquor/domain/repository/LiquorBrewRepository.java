package shop.soolsool.liquor.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.soolsool.liquor.domain.model.LiquorBrew;
import shop.soolsool.liquor.domain.model.vo.LiquorBrewType;

@Repository
public interface LiquorBrewRepository extends JpaRepository<LiquorBrew, Long> {

    Optional<LiquorBrew> findByType(final LiquorBrewType type);
}

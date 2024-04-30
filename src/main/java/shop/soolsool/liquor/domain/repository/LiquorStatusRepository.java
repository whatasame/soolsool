package shop.soolsool.liquor.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.soolsool.liquor.domain.model.LiquorStatus;
import shop.soolsool.liquor.domain.model.vo.LiquorStatusType;

@Repository
public interface LiquorStatusRepository extends JpaRepository<LiquorStatus, Long> {

    Optional<LiquorStatus> findByType(final LiquorStatusType type);
}

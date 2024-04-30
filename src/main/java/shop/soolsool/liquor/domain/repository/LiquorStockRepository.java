package shop.soolsool.liquor.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.soolsool.liquor.domain.model.LiquorStock;

@Repository
public interface LiquorStockRepository extends JpaRepository<LiquorStock, Long> {

    @Query("select ls from LiquorStock ls "
            + "where ls.liquorId = :liquorId and ls.expiredAt > now() "
            + "order by ls.expiredAt asc")
    List<LiquorStock> findAllByLiquorIdNotExpired(final Long liquorId);
}

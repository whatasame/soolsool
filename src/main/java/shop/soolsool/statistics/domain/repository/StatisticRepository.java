package shop.soolsool.statistics.domain.repository;

import org.springframework.stereotype.Repository;
import shop.soolsool.statistics.domain.model.StatisticLiquors;

@Repository
public interface StatisticRepository {

    StatisticLiquors findTop5LiquorsBySalePrice();

    StatisticLiquors findTop5LiquorsBySaleQuantity();

    void updateStatistic();
}

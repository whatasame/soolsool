package shop.soolsool.statistics.infrastructure;

import org.springframework.stereotype.Component;
import shop.soolsool.statistics.domain.model.StatisticLiquors;

@Component
public interface StatisticRedis {

    StatisticLiquors findTop5StatisticLiquorsBySalePrice();

    StatisticLiquors saveTop5StatisticLiquorsBySalePrice(final StatisticLiquors statisticLiquors);

    StatisticLiquors findTop5StatisticLiquorsBySaleQuantity();

    StatisticLiquors saveTop5StatisticLiquorsBySaleQuantity(final StatisticLiquors statisticLiquors);
}

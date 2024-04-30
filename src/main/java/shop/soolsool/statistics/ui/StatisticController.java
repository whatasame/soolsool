package shop.soolsool.statistics.ui;

import static org.springframework.http.HttpStatus.OK;
import static shop.soolsool.statistics.code.StatisticsResultCode.STATISTIC_TOP5_SALE_PRICE;
import static shop.soolsool.statistics.code.StatisticsResultCode.STATISTIC_TOP5_SALE_QUANTITY;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.soolsool.auth.ui.dto.NoAuth;
import shop.soolsool.core.aop.RequestLogging;
import shop.soolsool.core.common.ApiResponse;
import shop.soolsool.statistics.application.StatisticService;
import shop.soolsool.statistics.ui.response.LiquorSalePriceResponse;
import shop.soolsool.statistics.ui.response.LiquorSaleQuantityResponse;

@RestController
@Slf4j
@RequestMapping("/statistic")
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;

    @NoAuth
    @RequestLogging
    @GetMapping("/price")
    public ResponseEntity<ApiResponse<List<LiquorSalePriceResponse>>> findTop5LiquorsBySalePrice() {
        final List<LiquorSalePriceResponse> liquorSalePriceResponses = statisticService.findTop5LiquorsBySalePrice();

        return ResponseEntity.status(OK).body(ApiResponse.of(STATISTIC_TOP5_SALE_PRICE, liquorSalePriceResponses));
    }

    @NoAuth
    @RequestLogging
    @GetMapping("/quantity")
    public ResponseEntity<ApiResponse<List<LiquorSaleQuantityResponse>>> findTop5LiquorsBySaleQuantity() {
        final List<LiquorSaleQuantityResponse> liquorSaleQuantityResponses =
                statisticService.findTop5LiquorsBySaleQuantity();

        return ResponseEntity.status(OK)
                .body(ApiResponse.of(STATISTIC_TOP5_SALE_QUANTITY, liquorSaleQuantityResponses));
    }
}

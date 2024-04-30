package shop.soolsool.liquor.ui;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.soolsool.auth.ui.dto.Vendor;
import shop.soolsool.core.aop.RequestLogging;
import shop.soolsool.core.common.ApiResponse;
import shop.soolsool.core.common.LiquorResultCode;
import shop.soolsool.liquor.application.LiquorStockService;
import shop.soolsool.liquor.ui.dto.LiquorStockSaveRequest;

@RestController
@Slf4j
@RequestMapping("/liquor-stocks")
@RequiredArgsConstructor
public class LiquorStockController {

    private final LiquorStockService liquorStockService;

    @Vendor
    @RequestLogging
    @PutMapping
    public ResponseEntity<ApiResponse<Void>> saveLiquorStock(@RequestBody final LiquorStockSaveRequest request) {
        liquorStockService.saveLiquorStock(request);

        return ResponseEntity.ok(ApiResponse.from(LiquorResultCode.LIQUOR_STOCK_SAVED));
    }
}

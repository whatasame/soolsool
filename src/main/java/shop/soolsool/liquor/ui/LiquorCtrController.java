package shop.soolsool.liquor.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.soolsool.auth.ui.dto.NoAuth;
import shop.soolsool.core.aop.RequestLogging;
import shop.soolsool.core.common.ApiResponse;
import shop.soolsool.liquor.application.LiquorCtrService;
import shop.soolsool.liquor.code.LiquorCtrResultCode;
import shop.soolsool.liquor.ui.dto.LiquorClickAddRequest;
import shop.soolsool.liquor.ui.dto.LiquorCtrDetailResponse;
import shop.soolsool.liquor.ui.dto.LiquorImpressionAddRequest;

@RestController
@RequestMapping("/liquor-ctr")
@RequiredArgsConstructor
public class LiquorCtrController {

    private final LiquorCtrService liquorCtrService;

    @NoAuth
    @RequestLogging
    @GetMapping
    public ResponseEntity<ApiResponse<LiquorCtrDetailResponse>> findLiquorCtr(@RequestParam final Long liquorId) {
        return ResponseEntity.ok(ApiResponse.of(
                LiquorCtrResultCode.FIND_LIQUOR_CTR_SUCCESS,
                new LiquorCtrDetailResponse(liquorCtrService.getLiquorCtrByLiquorId(liquorId))));
    }

    @NoAuth
    @RequestLogging
    @PatchMapping("/impressions")
    public ResponseEntity<ApiResponse<Void>> increaseImpression(@RequestBody final LiquorImpressionAddRequest request) {
        liquorCtrService.increaseImpression(request);

        return ResponseEntity.ok(ApiResponse.from(LiquorCtrResultCode.INCREASE_IMPRESSION_SUCCESS));
    }

    @NoAuth
    @RequestLogging
    @PatchMapping("/clicks")
    public ResponseEntity<ApiResponse<Void>> increaseClick(@RequestBody final LiquorClickAddRequest request) {
        liquorCtrService.increaseClick(request);

        return ResponseEntity.ok(ApiResponse.from(LiquorCtrResultCode.INCREASE_CLICK_SUCCESS));
    }
}

package shop.soolsool.member.ui;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.soolsool.auth.ui.dto.LoginUser;
import shop.soolsool.core.aop.RequestLogging;
import shop.soolsool.core.common.ApiResponse;
import shop.soolsool.member.application.MemberMileageService;
import shop.soolsool.member.code.MemberResultCode;
import shop.soolsool.member.ui.dto.MemberMileageChargeRequest;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberMileageController {

    private final MemberMileageService memberMileageService;

    @RequestLogging
    @PatchMapping("/mileage")
    public ResponseEntity<ApiResponse<Void>> addMemberMileage(
            @LoginUser final Long memberId,
            @RequestBody @Valid final MemberMileageChargeRequest memberMileageChargeRequest) {
        memberMileageService.addMemberMileage(memberId, memberMileageChargeRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.from(MemberResultCode.MEMBER_MILEAGE_CHARGE_SUCCESS));
    }
}

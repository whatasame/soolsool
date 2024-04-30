package shop.soolsool.member.ui;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.soolsool.auth.ui.dto.LoginUser;
import shop.soolsool.auth.ui.dto.NoAuth;
import shop.soolsool.core.aop.RequestLogging;
import shop.soolsool.core.common.ApiResponse;
import shop.soolsool.member.application.MemberService;
import shop.soolsool.member.code.MemberResultCode;
import shop.soolsool.member.ui.dto.MemberAddRequest;
import shop.soolsool.member.ui.dto.MemberDetailResponse;
import shop.soolsool.member.ui.dto.MemberModifyRequest;

@RestController
@Slf4j
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @NoAuth
    @RequestLogging
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> addMember(@RequestBody @Valid final MemberAddRequest memberAddRequest) {
        memberService.addMember(memberAddRequest);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.from(MemberResultCode.MEMBER_CREATE_SUCCESS));
    }

    @RequestLogging
    @GetMapping
    public ResponseEntity<ApiResponse<MemberDetailResponse>> findMemberDetails(@LoginUser final Long memberId) {
        final MemberDetailResponse memberDetailResponse = memberService.findMember(memberId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.of(MemberResultCode.MEMBER_FIND_SUCCESS, memberDetailResponse));
    }

    @RequestLogging
    @PatchMapping
    public ResponseEntity<ApiResponse<Void>> modifyMember(
            @LoginUser final Long memberId, @RequestBody @Valid final MemberModifyRequest memberModifyRequest) {
        memberService.modifyMember(memberId, memberModifyRequest);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.from(MemberResultCode.MEMBER_MODIFY_SUCCESS));
    }

    @RequestLogging
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> removeMember(@LoginUser final Long memberId) {
        memberService.removeMember(memberId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.from(MemberResultCode.MEMBER_DELETE_SUCCESS));
    }
}

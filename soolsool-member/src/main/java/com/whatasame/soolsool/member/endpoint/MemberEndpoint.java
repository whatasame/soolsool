package com.whatasame.soolsool.member.endpoint;

import com.whatasame.soolsool.jwt.model.MemberAuthentication;
import com.whatasame.soolsool.member.command.CreateMember;
import com.whatasame.soolsool.member.dto.MemberResponse;
import com.whatasame.soolsool.member.service.MemberService;
import com.whatasame.soolsool.rest.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberEndpoint {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<ApiResult<Void>> createMember(@RequestBody final CreateMember command) {
        memberService.createMember(command);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResult<MemberResponse>> me(@AuthenticationPrincipal MemberAuthentication authentication) {
        MemberResponse result = memberService.me(authentication.getMemberId());

        return ResponseEntity.ok(ApiResult.succeed(result));
    }
}

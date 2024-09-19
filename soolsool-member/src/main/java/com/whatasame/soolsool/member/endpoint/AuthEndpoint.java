package com.whatasame.soolsool.member.endpoint;

import com.whatasame.soolsool.jwt.jwt.model.AuthToken;
import com.whatasame.soolsool.member.command.EmailLogin;
import com.whatasame.soolsool.member.service.AuthService;
import com.whatasame.soolsool.rest.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthEndpoint {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResult<AuthToken>> login(@RequestBody final EmailLogin command) {
        final AuthToken token = authService.login(command);

        return ResponseEntity.ok(ApiResult.succeed(token));
    }

    @GetMapping("/logout")
    public void logout() {}

    // 리프레쉬 토큰을 이용한 토큰 재발급
    @PostMapping("/refresh")
    public void reissue() {}
}

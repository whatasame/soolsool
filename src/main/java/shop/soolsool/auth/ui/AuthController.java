package shop.soolsool.auth.ui;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.soolsool.auth.application.AuthService;
import shop.soolsool.auth.code.AuthResultCode;
import shop.soolsool.auth.ui.dto.LoginRequest;
import shop.soolsool.auth.ui.dto.LoginResponse;
import shop.soolsool.core.common.ApiResponse;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            final HttpServletRequest httpServletRequest, @RequestBody final LoginRequest loginRequest) {
        log.info(
                "{} {} | request : {}",
                httpServletRequest.getMethod(),
                httpServletRequest.getServletPath(),
                loginRequest);

        final LoginResponse token = authService.createToken(loginRequest);

        return ResponseEntity.ok(ApiResponse.of(AuthResultCode.LOGIN_SUCCESS, token));
    }
}

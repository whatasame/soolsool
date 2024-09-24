package com.whatasame.endpoint;

import com.whatasame.soolsool.jwt.model.MemberAuthentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestEndpoint {

    @GetMapping("/test")
    public String test(@AuthenticationPrincipal final MemberAuthentication authentication) {
        return "Test success! Your memeber ID: " + authentication.getMemberId();
    }
}

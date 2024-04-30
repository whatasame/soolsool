package shop.soolsool.auth.code;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shop.soolsool.core.code.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    TOKEN_ERROR(BAD_REQUEST.value(), "A101", "토큰이 유효하지 않습니다."),
    INVALID_AUTHORITY(UNAUTHORIZED.value(), "A102", "권한이 없습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}

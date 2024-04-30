package shop.soolsool.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shop.soolsool.core.code.ErrorCode;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private final String message;
    private final String code;
    private final int status;

    public ErrorResponse(final ErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
        this.status = errorCode.getStatus();
    }

    public static ErrorResponse from(final ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }
}

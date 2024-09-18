package com.whatasame.soolsool.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResult<String>> handleException(final Exception e) {
        log.error("Unexpected exception occurred", e);

        return ResponseEntity.internalServerError().body(ApiResult.fail("잠시 후 시도해주세요."));
    }
}

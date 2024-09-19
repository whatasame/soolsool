package com.whatasame.soolsool.rest;

public record ApiResult<T>(T data, String error) {

    public static <T> ApiResult<T> succeed(final T data) {
        return new ApiResult<>(data, null);
    }

    public static ApiResult<String> fail(final String error) {
        return new ApiResult<>(null, error);
    }
}

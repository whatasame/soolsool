package shop.soolsool.core.infra;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RedisCacheType {
    LIQUOR_FIRST_PAGE("liquorsFirstPage", 60L),
    ;

    private final String cacheName;
    private final Long expireSeconds;
}

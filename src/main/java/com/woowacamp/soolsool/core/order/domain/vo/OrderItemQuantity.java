package com.woowacamp.soolsool.core.order.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class OrderItemQuantity {

    private final int quantity;
}

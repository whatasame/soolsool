package shop.soolsool.receipt.domain.model.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReceiptStatusType {
    INPROGRESS("INPROGRESS"),
    CANCELED("CANCELED"),
    COMPLETED("COMPLETED"),
    EXPIRED("EXPIRED"),
    ;

    private final String name;
}

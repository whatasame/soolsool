package shop.soolsool.order.application;

import java.math.BigInteger;

public interface OrderMemberService {

    void refundMileage(final Long memberId, final BigInteger mileage);
}

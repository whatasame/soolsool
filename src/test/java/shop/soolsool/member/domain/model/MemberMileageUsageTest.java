package shop.soolsool.member.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;

import java.math.BigInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shop.soolsool.member.domain.model.vo.MemberRoleType;
import shop.soolsool.order.domain.model.Order;
import shop.soolsool.order.domain.model.OrderStatus;
import shop.soolsool.receipt.domain.model.Receipt;

@DisplayName("단위 테스트: MemberMileageUsage")
class MemberMileageUsageTest {

    @Test
    @DisplayName("엔티티 정상 생성")
    void createMemberMileage() {
        // given
        final String usage = "1000";

        final String email = "woowatechcamp@woowafriends.com";
        final String password = "q1w2e3r4!";
        final String name = "솔라";
        final String phoneNumber = "010-1234-5678";
        final String mileage = "0";
        final String address = "서울 송파구 올림픽로 295 7층";
        final MemberRole role = new MemberRole(MemberRoleType.CUSTOMER);
        final Member member = Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .phoneNumber(phoneNumber)
                .mileage(mileage)
                .address(address)
                .role(role)
                .build();
        final OrderStatus orderStatus = mock(OrderStatus.class);
        final Receipt receipt = mock(Receipt.class);

        final Order order = Order.builder()
                .memberId(1L)
                .orderStatus(orderStatus)
                .receipt(receipt)
                .build();

        // when
        final MemberMileageUsage memberMileageUsage = MemberMileageUsage.builder()
                .member(member)
                .order(order)
                .amount(new BigInteger(usage))
                .build();

        // then
        assertAll(
                () -> assertThat(memberMileageUsage.getMember())
                        .usingRecursiveComparison()
                        .isEqualTo(member),
                () -> assertThat(memberMileageUsage.getOrder())
                        .usingRecursiveComparison()
                        .isEqualTo(order),
                () -> assertThat(memberMileageUsage.getAmount().getMileage()).isEqualTo(usage));
    }
}

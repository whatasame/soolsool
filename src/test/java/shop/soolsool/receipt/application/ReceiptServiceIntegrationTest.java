package shop.soolsool.receipt.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static shop.soolsool.cart.code.CartErrorCode.NOT_FOUND_CART_ITEM;
import static shop.soolsool.member.code.MemberErrorCode.MEMBER_NO_INFORMATION;
import static shop.soolsool.receipt.code.ReceiptErrorCode.NOT_EQUALS_MEMBER;
import static shop.soolsool.receipt.code.ReceiptErrorCode.NOT_RECEIPT_FOUND;
import static shop.soolsool.receipt.domain.model.vo.ReceiptStatusType.COMPLETED;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import shop.soolsool.cart.application.CartService;
import shop.soolsool.core.config.MultipleCacheManagerConfig;
import shop.soolsool.core.config.QuerydslConfig;
import shop.soolsool.core.config.RedissonConfig;
import shop.soolsool.core.exception.SoolSoolException;
import shop.soolsool.liquor.application.LiquorService;
import shop.soolsool.liquor.domain.repository.LiquorBrewCache;
import shop.soolsool.liquor.domain.repository.LiquorCtrRedisRepository;
import shop.soolsool.liquor.domain.repository.LiquorQueryDslRepository;
import shop.soolsool.liquor.domain.repository.LiquorRegionCache;
import shop.soolsool.liquor.domain.repository.LiquorStatusCache;
import shop.soolsool.receipt.domain.repository.ReceiptRedisRepository;
import shop.soolsool.receipt.domain.repository.ReceiptStatusCache;
import shop.soolsool.receipt.ui.response.ReceiptDetailResponse;

@DataJpaTest
@Import({
    ReceiptService.class,
    CartService.class,
    LiquorService.class,
    ReceiptMapper.class,
    LiquorBrewCache.class,
    LiquorStatusCache.class,
    LiquorRegionCache.class,
    ReceiptStatusCache.class,
    LiquorQueryDslRepository.class,
    QuerydslConfig.class,
    MultipleCacheManagerConfig.class,
    RedissonConfig.class,
    ReceiptRedisRepository.class,
    LiquorCtrRedisRepository.class
})
@DisplayName("통합 테스트: ReceiptService")
class ReceiptServiceIntegrationTest {

    @Autowired
    private ReceiptService receiptService;

    @Test
    @Sql({
        "/member-type.sql",
        "/member.sql",
        "/liquor-type.sql",
        "/liquor.sql",
        "/liquor-stock.sql",
        "/cart-item.sql",
        "/receipt-type.sql"
    })
    @DisplayName("주문서를 올바르게 생성한다.")
    void addReceiptTest() {
        // given
        final Long 김배달 = 1L;

        // when & then
        assertThatCode(() -> receiptService.addReceipt(김배달)).doesNotThrowAnyException();
    }

    @Test
    @Sql({
        "/member-type.sql",
        "/member.sql",
        "/liquor-type.sql",
        "/liquor.sql",
        "/liquor-stock.sql",
        "/cart-item.sql",
        "/receipt-type.sql"
    })
    @DisplayName("회원가 존재하고, 카트에 주문할 게 없으면, 주문서를 생성할 수 없다.")
    void addReceiptWithNoCartItems() {
        // given
        final Long 최민족 = 2L;

        // when & then
        assertThatCode(() -> receiptService.addReceipt(최민족))
                .isInstanceOf(SoolSoolException.class)
                .hasMessage(NOT_FOUND_CART_ITEM.getMessage());
    }

    @Test
    @Sql({
        "/member-type.sql",
        "/member.sql",
        "/liquor-type.sql",
        "/liquor.sql",
        "/liquor-stock.sql",
        "/cart-item.sql",
        "/receipt-type.sql"
    })
    @DisplayName("회원이 존재하지 않으면, 주문서를 생성할 수 없다.")
    void addReceiptWithNoMemberId() {
        // given
        final Long memberId = 999L;

        // when & then
        assertThatCode(() -> receiptService.addReceipt(memberId))
                .isInstanceOf(SoolSoolException.class)
                .hasMessage(MEMBER_NO_INFORMATION.getMessage());
    }

    @Test
    @Sql({
        "/member-type.sql",
        "/member.sql",
        "/liquor-type.sql",
        "/liquor.sql",
        "/liquor-stock.sql",
        "/cart-item.sql",
        "/receipt-type.sql",
        "/receipt.sql"
    })
    @DisplayName("주문서를 올바르게 조회한다.")
    void receiptDetails() {
        // given
        final Long 김배달 = 1L;
        final Long 김배달_주문서 = 1L;

        // when
        final ReceiptDetailResponse receipt = receiptService.findReceipt(김배달, 김배달_주문서);
        // then
        assertThat(receipt).extracting("id").isEqualTo(김배달_주문서);
    }

    @Test
    @Sql({
        "/member-type.sql",
        "/member.sql",
        "/liquor-type.sql",
        "/liquor.sql",
        "/liquor-stock.sql",
        "/cart-item.sql",
        "/receipt-type.sql",
        "/receipt.sql"
    })
    @DisplayName("회원가 다르면, 주문서가 조회되지 않는다.")
    void receiptDetailWithNoMemberId() {
        // given
        final Long 최민족 = 2L;
        final Long 김배달_주문서 = 1L;

        // when & then
        assertThatCode(() -> receiptService.findReceipt(최민족, 김배달_주문서))
                .isInstanceOf(SoolSoolException.class)
                .hasMessage(NOT_EQUALS_MEMBER.getMessage());
    }

    @Test
    @Sql({
        "/member-type.sql",
        "/member.sql",
        "/liquor-type.sql",
        "/liquor.sql",
        "/liquor-stock.sql",
        "/cart-item.sql",
        "/receipt-type.sql",
        "/receipt.sql"
    })
    @DisplayName("주문서가 없으면 올바르게 조회되지 않는다.")
    void receiptDetailWithNoItems() {
        // given
        final Long 김배달 = 1L;
        final Long 존재하지_않는_주문서 = 999L;

        // when & then
        assertThatCode(() -> receiptService.findReceipt(김배달, 존재하지_않는_주문서))
                .isInstanceOf(SoolSoolException.class)
                .hasMessage(NOT_RECEIPT_FOUND.getMessage());
    }

    @Test
    @Sql({
        "/member-type.sql",
        "/member.sql",
        "/liquor-type.sql",
        "/liquor.sql",
        "/liquor-stock.sql",
        "/cart-item.sql",
        "/receipt-type.sql",
        "/receipt.sql"
    })
    @DisplayName("주문서가 정상적으로 수정된다.")
    void receiptModifySuccess() {
        // given
        final Long 김배달 = 1L;
        final Long 김배달_주문서 = 1L;

        // when
        receiptService.modifyReceiptStatus(김배달, 김배달_주문서, COMPLETED);

        // then
        final ReceiptDetailResponse receipt = receiptService.findReceipt(김배달, 김배달_주문서);
        assertThat(receipt.getReceiptStatus()).isEqualTo("COMPLETED");
    }

    @Test
    @Sql({
        "/member-type.sql",
        "/member.sql",
        "/liquor-type.sql",
        "/liquor.sql",
        "/liquor-stock.sql",
        "/cart-item.sql",
        "/receipt-type.sql",
        "/receipt.sql"
    })
    @DisplayName("주문서의 주인이 아니면, 수정을 할 수 없다. ")
    void receiptModifyFailWithNotEqualMember() {
        // given
        final Long 최민족 = 2L;
        final Long 김배달_주문서 = 1L;

        // when & then
        assertThatCode(() -> receiptService.modifyReceiptStatus(최민족, 김배달_주문서, COMPLETED))
                .isInstanceOf(SoolSoolException.class)
                .hasMessage(NOT_EQUALS_MEMBER.getMessage());
    }

    @Test
    @Sql({
        "/member-type.sql",
        "/member.sql",
        "/liquor-type.sql",
        "/liquor.sql",
        "/liquor-stock.sql",
        "/cart-item.sql",
        "/receipt-type.sql",
        "/receipt.sql"
    })
    @DisplayName("주문서가 없으면,주문서의 상태를 수정을 할 수 없다. ")
    void receiptModifyFailWithNotExistReceipt() {
        // given
        final Long 김배달 = 1L;
        final Long 존재하지_않는_주문서 = 999L;

        // when & then
        assertThatCode(() -> receiptService.modifyReceiptStatus(김배달, 존재하지_않는_주문서, COMPLETED))
                .isInstanceOf(SoolSoolException.class)
                .hasMessage(NOT_RECEIPT_FOUND.getMessage());
    }
}

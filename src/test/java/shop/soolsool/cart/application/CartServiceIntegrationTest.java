package shop.soolsool.cart.application;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static shop.soolsool.cart.code.CartErrorCode.INVALID_QUANTITY_SIZE;
import static shop.soolsool.cart.code.CartErrorCode.NOT_EQUALS_MEMBER;
import static shop.soolsool.cart.code.CartErrorCode.NOT_FOUND_CART_ITEM;
import static shop.soolsool.cart.code.CartErrorCode.NOT_FOUND_LIQUOR;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import shop.soolsool.cart.ui.dto.request.CartItemModifyRequest;
import shop.soolsool.cart.ui.dto.request.CartItemSaveRequest;
import shop.soolsool.core.config.QuerydslConfig;
import shop.soolsool.core.config.RedisTestConfig;
import shop.soolsool.core.exception.SoolSoolException;
import shop.soolsool.liquor.application.LiquorService;
import shop.soolsool.liquor.domain.repository.LiquorBrewCache;
import shop.soolsool.liquor.domain.repository.LiquorCtrRedisRepository;
import shop.soolsool.liquor.domain.repository.LiquorQueryDslRepository;
import shop.soolsool.liquor.domain.repository.LiquorRegionCache;
import shop.soolsool.liquor.domain.repository.LiquorStatusCache;

@DataJpaTest
@Import({
    CartService.class,
    LiquorService.class,
    LiquorBrewCache.class,
    LiquorStatusCache.class,
    LiquorRegionCache.class,
    LiquorQueryDslRepository.class,
    LiquorStatusCache.class,
    LiquorRegionCache.class,
    QuerydslConfig.class,
    LiquorCtrRedisRepository.class,
    RedisTestConfig.class
})
@DisplayName("통합 테스트: CartItemService")
class CartServiceIntegrationTest {

    @Autowired
    private CartService cartService;

    @Test
    @Sql({
        "/member-type.sql", "/member.sql",
        "/liquor-type.sql", "/liquor.sql",
    })
    @DisplayName("존재하지 않는 술을 장바구니 상품으로 추가할 경우 예외를 던진다.")
    void saveNotExistsLiquorByCartItem() {
        // given
        final Long 김배달 = 1L;
        final CartItemSaveRequest request = new CartItemSaveRequest(99999L, 1);

        // when & then
        assertThatThrownBy(() -> cartService.addCartItem(김배달, request))
                .isExactlyInstanceOf(SoolSoolException.class)
                .hasMessage(NOT_FOUND_LIQUOR.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    @Sql({
        "/member-type.sql", "/member.sql",
        "/liquor-type.sql", "/liquor.sql",
        "/cart-item.sql"
    })
    @DisplayName("장바구니 수량 변경으로 음수나 0이 들어올 경우 예외를 던진다.")
    void modifyCartItemQuantity(final Integer quantity) {
        // given
        final Long 김배달 = 1L;
        final Long 김배달_장바구니_상품 = 1L;

        final CartItemModifyRequest cartItemModifyRequest = new CartItemModifyRequest(quantity);

        // when & then
        assertThatCode(() -> cartService.modifyCartItemQuantity(김배달, 김배달_장바구니_상품, cartItemModifyRequest))
                .isInstanceOf(SoolSoolException.class)
                .hasMessage(INVALID_QUANTITY_SIZE.getMessage());
    }

    @Test
    @Sql({
        "/member-type.sql", "/member.sql",
        "/liquor-type.sql", "/liquor.sql",
        "/cart-item.sql"
    })
    @DisplayName("다른 사용자가 삭제하려고 할 시, 예외를 던진다.")
    void removeCartItemByNotEqualUser() {
        // given
        final Long 최민족 = 2L;
        final Long 김배달_장바구니_상품 = 1L;

        // when & then
        assertThatCode(() -> cartService.removeCartItem(최민족, 김배달_장바구니_상품))
                .isInstanceOf(SoolSoolException.class)
                .hasMessage(NOT_EQUALS_MEMBER.getMessage());
    }

    @Test
    @Sql({
        "/member-type.sql", "/member.sql",
        "/liquor-type.sql", "/liquor.sql",
        "/cart-item.sql"
    })
    @DisplayName("장바구니에 없는 상품을 삭제할 시, 예외를 던진다.")
    void removeNoExistCartItem() {
        // given
        final Long memberId = 1L;
        final Long wrongCartItemId = 999L;

        // when & then
        assertThatCode(() -> cartService.removeCartItem(memberId, wrongCartItemId))
                .isInstanceOf(SoolSoolException.class)
                .hasMessage(NOT_FOUND_CART_ITEM.getMessage());
    }
}

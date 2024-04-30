package shop.soolsool.cart.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import shop.soolsool.cart.domain.model.CartItem;
import shop.soolsool.liquor.domain.model.Liquor;
import shop.soolsool.liquor.domain.repository.LiquorRepository;
import shop.soolsool.member.domain.repository.MemberRepository;

@DataJpaTest
@DisplayName("통합 테스트: CartItemRepository")
class CartItemRepositoryTest {

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    LiquorRepository liquorRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Sql({"/member-type.sql", "/member.sql", "/liquor-type.sql", "/liquor.sql"})
    @DisplayName("CartItem을 저장한다.")
    void createCartItem() {
        // given
        final Liquor 새로 =
                liquorRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("테스트 데이터가 없습니다."));

        final CartItem cartItem =
                CartItem.builder().memberId(1L).liquor(새로).quantity(2).build();

        // when
        final CartItem saved = cartItemRepository.save(cartItem);

        // then
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    @Sql({"/member-type.sql", "/member.sql", "/liquor-type.sql", "/liquor.sql", "/liquor-stock.sql", "/cart-item.sql"})
    @DisplayName("유저 아이디에 따른 장바구니 모두 조회")
    void cartItemListByUserId() {
        // given

        // when
        final List<CartItem> cartItemList = cartItemRepository.findAllByMemberIdOrderByCreatedAtDescWithLiquor(1L);

        // then
        assertThat(cartItemList).hasSize(2);
    }

    @Test
    @Sql({
        "/member-type.sql", "/member.sql",
        "/liquor-type.sql", "/liquor.sql",
        "/cart-item.sql"
    })
    @DisplayName("유저 아이디에 따른 장바구니 모든 아이템 삭제")
    void deleteAllTest() {
        // given

        // when
        cartItemRepository.deleteAllByMemberId(1L);

        // then
        assertThat(cartItemRepository.findAllByMemberIdOrderByCreatedAtDesc(1L)).isEmpty();
    }
}

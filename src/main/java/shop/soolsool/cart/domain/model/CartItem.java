package shop.soolsool.cart.domain.model;

import static lombok.AccessLevel.PROTECTED;
import static shop.soolsool.core.code.GlobalErrorCode.NO_CONTENT;

import java.math.BigInteger;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import shop.soolsool.cart.domain.model.converter.CartItemQuantityConverter;
import shop.soolsool.cart.domain.model.vo.CartItemQuantity;
import shop.soolsool.core.common.BaseEntity;
import shop.soolsool.core.exception.SoolSoolException;
import shop.soolsool.liquor.domain.model.Liquor;

@Entity
@Table(name = "cart_items")
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
public class CartItem extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(name = "member_id", nullable = false)
    @Getter
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liquor_id", nullable = false)
    @Getter
    private Liquor liquor;

    @ColumnDefault("1")
    @Column(name = "quantity", nullable = false)
    @Convert(converter = CartItemQuantityConverter.class)
    private CartItemQuantity quantity;

    @Builder
    public CartItem(final Long memberId, final Liquor liquor, final int quantity) {
        validateIsNotNullLiquor(liquor);

        this.memberId = memberId;
        this.liquor = liquor;
        this.quantity = new CartItemQuantity(quantity);
    }

    private void validateIsNotNullLiquor(final Liquor liquor) {
        if (Objects.isNull(liquor)) {
            throw new SoolSoolException(NO_CONTENT);
        }
    }

    public boolean hasSameLiquorWith(final CartItem other) {
        if (liquor == null || other.liquor == null) {
            return false;
        }

        return liquor.equals(other.liquor);
    }

    public boolean hasDifferentMemberIdWith(final Long otherMemberId) {
        return !memberId.equals(otherMemberId);
    }

    public boolean hasStoppedLiquor() {
        return liquor.isStopped();
    }

    public void updateQuantity(final Integer liquorQuantity) {
        this.quantity = new CartItemQuantity(liquorQuantity);
    }

    public int getQuantity() {
        return this.quantity.getQuantity();
    }

    public BigInteger getLiquorPrice() {
        return this.liquor.getPrice();
    }
}

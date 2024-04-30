package shop.soolsool.liquor.domain.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.soolsool.core.common.BaseEntity;
import shop.soolsool.liquor.domain.model.converter.LiquorStockCountConverter;
import shop.soolsool.liquor.domain.model.vo.LiquorStockCount;

@Entity
@Table(name = "liquor_stocks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LiquorStock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(name = "liquor_id", nullable = false)
    @Getter
    private Long liquorId;

    @Column(name = "stock", nullable = false)
    @Convert(converter = LiquorStockCountConverter.class)
    private LiquorStockCount stock;

    @Column(name = "expired_at", nullable = false)
    @Getter
    private LocalDateTime expiredAt;

    @Builder
    public LiquorStock(final Long liquorId, final LiquorStockCount stock, final LocalDateTime expiredAt) {
        this.liquorId = liquorId;
        this.stock = stock;
        this.expiredAt = expiredAt;
    }

    public int getStock() {
        return this.stock.getStock();
    }

    public void decreaseStock(final int quantity) {
        this.stock = this.stock.decrease(quantity);
    }

    public boolean isOutOfStock() {
        return this.stock.isZero();
    }
}

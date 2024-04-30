package shop.soolsool.member.domain.model;

import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.soolsool.core.common.BaseEntity;
import shop.soolsool.member.domain.model.converter.MemberMileageConverter;
import shop.soolsool.member.domain.model.vo.MemberMileage;
import shop.soolsool.order.domain.model.Order;

@Entity
@Table(name = "member_mileage_usages")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberMileageUsage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "amount", nullable = false)
    @Convert(converter = MemberMileageConverter.class)
    private MemberMileage amount;

    @Builder
    public MemberMileageUsage(final Member member, final Order order, final BigInteger amount) {
        this.member = member;
        this.order = order;
        this.amount = new MemberMileage(amount);
    }
}

package shop.soolsool.member.ui.dto;

import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import shop.soolsool.member.domain.model.Member;
import shop.soolsool.member.domain.model.MemberMileageCharge;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberMileageChargeRequest {

    private BigInteger amount;

    public MemberMileageCharge toMemberMileageCharge(final Member member) {
        return MemberMileageCharge.builder().member(member).amount(amount).build();
    }
}

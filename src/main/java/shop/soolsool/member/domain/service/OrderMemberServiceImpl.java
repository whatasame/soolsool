package shop.soolsool.member.domain.service;

import java.math.BigInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import shop.soolsool.core.exception.SoolSoolException;
import shop.soolsool.member.code.MemberErrorCode;
import shop.soolsool.member.domain.model.Member;
import shop.soolsool.member.domain.repository.MemberRepository;
import shop.soolsool.order.application.OrderMemberService;

@Component
@RequiredArgsConstructor
public class OrderMemberServiceImpl implements OrderMemberService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void refundMileage(final Long memberId, final BigInteger mileage) {
        final Member member = memberRepository
                .findById(memberId)
                .orElseThrow(() -> new SoolSoolException(MemberErrorCode.MEMBER_NO_INFORMATION));

        member.updateMileage(mileage);
    }
}

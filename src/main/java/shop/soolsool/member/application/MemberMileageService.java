package shop.soolsool.member.application;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.soolsool.core.exception.SoolSoolException;
import shop.soolsool.core.infra.LockType;
import shop.soolsool.member.code.MemberErrorCode;
import shop.soolsool.member.domain.model.Member;
import shop.soolsool.member.domain.model.MemberMileageCharge;
import shop.soolsool.member.domain.model.MemberMileageUsage;
import shop.soolsool.member.domain.repository.MemberMileageChargeRepository;
import shop.soolsool.member.domain.repository.MemberMileageUsageRepository;
import shop.soolsool.member.domain.repository.MemberRepository;
import shop.soolsool.member.ui.dto.MemberMileageChargeRequest;
import shop.soolsool.order.domain.model.Order;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberMileageService {

    private static final long LOCK_WAIT_TIME = 3L;
    private static final long LOCK_LEASE_TIME = 3L;

    private final MemberRepository memberRepository;
    private final MemberMileageChargeRepository memberMileageChargeRepository;
    private final MemberMileageUsageRepository memberMileageUsageRepository;

    private final RedissonClient redissonClient;

    @Transactional
    public void addMemberMileage(final Long memberId, final MemberMileageChargeRequest memberMileageChargeRequest) {
        final RLock rLock = getMemberLock(memberId);

        try {
            rLock.tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS);

            final Member member = this.memberRepository
                    .findById(memberId)
                    .orElseThrow(() -> new SoolSoolException(MemberErrorCode.MEMBER_NO_INFORMATION));

            member.updateMileage(memberMileageChargeRequest.getAmount());

            final MemberMileageCharge memberMileageCharge = memberMileageChargeRequest.toMemberMileageCharge(member);

            this.memberMileageChargeRepository.save(memberMileageCharge);
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();

            throw new SoolSoolException(MemberErrorCode.INTERRUPTED_THREAD);
        } finally {
            unlock(rLock);
        }
    }

    private RLock getMemberLock(final Long memberId) {
        return this.redissonClient.getLock(LockType.MEMBER.getPrefix() + memberId);
    }

    private void unlock(final RLock rLock) {
        if (rLock.isLocked() && rLock.isHeldByCurrentThread()) {
            rLock.unlock();
        }
    }

    @Transactional
    public void subtractMemberMileage(final Long memberId, final Order order, final BigInteger mileageUsage) {
        final Member member = this.memberRepository
                .findById(memberId)
                .orElseThrow(() -> new SoolSoolException(MemberErrorCode.NOT_FOUND_RECEIPT));

        member.decreaseMileage(mileageUsage);

        this.memberMileageUsageRepository.save(new MemberMileageUsage(member, order, mileageUsage));
    }
}

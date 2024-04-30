package shop.soolsool.member.application;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.soolsool.core.exception.SoolSoolException;
import shop.soolsool.member.code.MemberErrorCode;
import shop.soolsool.member.domain.model.Member;
import shop.soolsool.member.domain.model.MemberRole;
import shop.soolsool.member.domain.model.vo.MemberEmail;
import shop.soolsool.member.domain.model.vo.MemberRoleType;
import shop.soolsool.member.domain.repository.MemberRepository;
import shop.soolsool.member.domain.repository.MemberRoleRepository;
import shop.soolsool.member.ui.dto.MemberAddRequest;
import shop.soolsool.member.ui.dto.MemberDetailResponse;
import shop.soolsool.member.ui.dto.MemberModifyRequest;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;

    @Transactional
    public void addMember(final MemberAddRequest memberAddRequest) {
        checkDuplicatedEmail(memberAddRequest.getEmail());

        final MemberRole memberRole = getMemberRole(memberAddRequest.getMemberRoleType());
        final Member member = memberAddRequest.toMember(memberRole);
        memberRepository.save(member);
    }

    private void checkDuplicatedEmail(final String email) {
        final Optional<Member> duplicatedEmil = memberRepository.findByEmail(new MemberEmail(email));

        if (duplicatedEmil.isPresent()) {
            throw new SoolSoolException(MemberErrorCode.MEMBER_DUPLICATED_EMAIL);
        }
    }

    private MemberRole getMemberRole(final String memberRequestRoleType) {
        final MemberRoleType memberRoleType = Arrays.stream(MemberRoleType.values())
                .filter(type -> Objects.equals(type.getType(), memberRequestRoleType))
                .findFirst()
                .orElse(MemberRoleType.CUSTOMER);
        log.info("memberRoleType : {}", memberRoleType);
        return memberRoleRepository
                .findByName(memberRoleType)
                .orElseThrow(() -> new SoolSoolException(MemberErrorCode.MEMBER_NO_ROLE_TYPE));
    }

    @Transactional(readOnly = true)
    public MemberDetailResponse findMember(final Long memberId) {
        final Member member = memberRepository
                .findById(memberId)
                .orElseThrow(() -> new SoolSoolException(MemberErrorCode.MEMBER_NO_INFORMATION));

        return MemberDetailResponse.from(member);
    }

    @Transactional
    public void modifyMember(final Long memberId, final MemberModifyRequest memberModifyRequest) {
        final Member member = memberRepository
                .findById(memberId)
                .orElseThrow(() -> new SoolSoolException(MemberErrorCode.MEMBER_NO_INFORMATION));

        member.update(memberModifyRequest);
    }

    @Transactional
    public void removeMember(final Long memberId) {
        final Member member = memberRepository
                .findById(memberId)
                .orElseThrow(() -> new SoolSoolException(MemberErrorCode.MEMBER_NO_INFORMATION));

        memberRepository.delete(member);
    }
}

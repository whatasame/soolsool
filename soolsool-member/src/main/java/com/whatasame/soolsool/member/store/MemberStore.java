package com.whatasame.soolsool.member.store;

import com.whatasame.soolsool.member.aggregate.Member;
import com.whatasame.soolsool.member.store.jpa.MemberJpaRepository;
import com.whatasame.soolsool.member.store.jpa.MemberJpo;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberStore {

    private final MemberJpaRepository memberJpaRepository;

    public void save(final Member member) {
        final MemberJpo memberJpo = new MemberJpo(member);

        memberJpaRepository.save(memberJpo);
    }

    public Member load(final String email) {
        final MemberJpo memberJpo = memberJpaRepository
                .findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        return memberJpo.toMember();
    }

    public Optional<Member> findByEmail(final String email) {
        return memberJpaRepository.findByEmail(email).map(MemberJpo::toMember);
    }

    public boolean isPresent(final String email) {
        return memberJpaRepository.existsByEmail(email);
    }
}

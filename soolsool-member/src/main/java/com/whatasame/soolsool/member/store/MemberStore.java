package com.whatasame.soolsool.member.store;

import com.whatasame.soolsool.member.aggregate.Member;
import com.whatasame.soolsool.member.store.jpa.MemberJpaRepository;
import com.whatasame.soolsool.member.store.jpa.MemberJpo;
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

    public boolean isPresent(final String email) {
        return memberJpaRepository.existsByEmail(email);
    }
}

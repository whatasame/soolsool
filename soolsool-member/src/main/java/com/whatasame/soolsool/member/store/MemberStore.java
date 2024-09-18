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

    public void save(Member member) {
        MemberJpo memberJpo = new MemberJpo(member);

        memberJpaRepository.save(memberJpo);
    }
}

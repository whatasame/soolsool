package com.whatasame.soolsool.member.service;

import com.whatasame.soolsool.member.aggregate.Member;
import com.whatasame.soolsool.member.aggregate.MemberRole;
import com.whatasame.soolsool.member.command.CreateMember;
import com.whatasame.soolsool.member.store.MemberStore;
import com.whatasame.soolsool.security.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberStore memberStore;

    private final JwtProvider jwtProvider;

    @Transactional
    public void createMember(final CreateMember command) {
        final Member member = new Member(
                null,
                MemberRole.ROLE_USER,
                command.email(),
                command.password(),
                command.name(),
                command.phone(),
                command.address());

        memberStore.save(member);
    }
}

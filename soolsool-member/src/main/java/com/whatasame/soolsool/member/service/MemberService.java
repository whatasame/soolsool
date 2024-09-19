package com.whatasame.soolsool.member.service;

import com.whatasame.soolsool.member.aggregate.Member;
import com.whatasame.soolsool.member.aggregate.MemberRole;
import com.whatasame.soolsool.member.command.CreateMember;
import com.whatasame.soolsool.member.store.MemberStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberStore memberStore;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createMember(final CreateMember command) {
        if (memberStore.isPresent(command.email())) {
            throw new IllegalArgumentException("중복된 이메일입니다."); // specify exception
        }

        final Member member = new Member(
                null,
                MemberRole.ROLE_USER,
                command.email(),
                passwordEncoder.encode(command.password()),
                command.name(),
                command.phone(),
                command.address());

        memberStore.save(member);
    }
}

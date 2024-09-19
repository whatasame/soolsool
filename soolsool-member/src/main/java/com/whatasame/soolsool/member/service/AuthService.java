package com.whatasame.soolsool.member.service;

import com.whatasame.soolsool.member.aggregate.Member;
import com.whatasame.soolsool.member.command.EmailLogin;
import com.whatasame.soolsool.member.store.MemberStore;
import com.whatasame.soolsool.security.jwt.JwtProvider;
import com.whatasame.soolsool.security.jwt.model.AuthToken;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberStore memberStore;

    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public AuthToken login(final EmailLogin command) {
        final Member member = memberStore.load(command.email());
        if (!passwordEncoder.matches(command.password(), member.password())) {
            throw new IllegalArgumentException("이메일 혹은 비밀번호가 일치하지 않습니다.");
        }

        return jwtProvider.createToken(member.id(), List.of(member.role().name()));
    }
}

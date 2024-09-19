package com.whatasame.soolsool.member.service;

import com.whatasame.soolsool.member.aggregate.Member;
import com.whatasame.soolsool.member.command.EmailLogin;
import com.whatasame.soolsool.member.store.MemberStore;
import com.whatasame.soolsool.security.jwt.JwtProvider;
import com.whatasame.soolsool.security.jwt.model.AuthToken;
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
        final String encodedPassword = passwordEncoder.encode(command.password());

        final Member member = memberStore.load(command.email(), encodedPassword);

        return jwtProvider.createToken(member.id());
    }
}

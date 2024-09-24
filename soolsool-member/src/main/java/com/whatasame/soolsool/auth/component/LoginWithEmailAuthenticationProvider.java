package com.whatasame.soolsool.auth.component;

import com.whatasame.soolsool.member.aggregate.Member;
import com.whatasame.soolsool.member.store.MemberStore;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginWithEmailAuthenticationProvider implements AuthenticationProvider {

    private final MemberStore memberStore;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String email = authentication.getName();
        final String password = (String) authentication.getCredentials();

        final Member member = memberStore
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("이메일에 해당하는 회원을 찾을 수 없습니다."));

        if (!passwordEncoder.matches(password, member.password())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        final List<GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority(member.role().name()));

        return new UsernamePasswordAuthenticationToken(member.id(), null, authorities);
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

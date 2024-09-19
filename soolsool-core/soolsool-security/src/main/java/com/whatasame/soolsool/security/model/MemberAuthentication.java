package com.whatasame.soolsool.security.model;

import java.util.Collection;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@ToString
@EqualsAndHashCode
public class MemberAuthentication implements Authentication {

    private final long memberId;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean authenticated;

    public MemberAuthentication(final long memberId, final Collection<? extends GrantedAuthority> authorities) {
        this.memberId = memberId;
        this.authorities = authorities;
        this.authenticated = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        throw new UnsupportedOperationException("Credential은 관리하지 않습니다.");
    }

    @Override
    public Object getDetails() {
        throw new UnsupportedOperationException("Details은 관리하지 않습니다.");
    }

    @Override
    public Object getPrincipal() {
        return memberId;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(final boolean isAuthenticated) throws IllegalArgumentException {
        throw new UnsupportedOperationException("인증 상태는 변경할 수 없으며 인스턴스는 항상 인증됨을 보장해야합니다.");
    }

    @Override
    public String getName() {
        return String.valueOf(memberId);
    }
}

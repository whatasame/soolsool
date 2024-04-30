package shop.soolsool.auth.application;

import static shop.soolsool.member.code.MemberErrorCode.MEMBER_NO_INFORMATION;
import static shop.soolsool.member.code.MemberErrorCode.MEMBER_NO_MATCH_PASSWORD;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.soolsool.auth.domain.TokenProvider;
import shop.soolsool.auth.ui.dto.LoginRequest;
import shop.soolsool.auth.ui.dto.LoginResponse;
import shop.soolsool.core.exception.SoolSoolException;
import shop.soolsool.member.domain.model.Member;
import shop.soolsool.member.domain.model.vo.MemberEmail;
import shop.soolsool.member.domain.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public LoginResponse createToken(final LoginRequest loginRequest) {
        final Member member = memberRepository
                .findByEmail(new MemberEmail(loginRequest.getEmail()))
                .orElseThrow(() -> new SoolSoolException(MEMBER_NO_INFORMATION));

        if (!member.matchPassword(loginRequest.getPassword())) {
            throw new SoolSoolException(MEMBER_NO_MATCH_PASSWORD);
        }

        return new LoginResponse(tokenProvider.createToken(member));
    }
}

package shop.soolsool.member.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import shop.soolsool.member.domain.model.Member;
import shop.soolsool.member.domain.model.vo.MemberEmail;

@DataJpaTest
@Sql({"/member-type.sql", "/member.sql"})
@DisplayName("통합 테스트: MemberRepository")
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("이메일로 member를 조회한다.")
    void memberCreateTest() {
        // given
        Optional<Member> member = memberRepository.findByEmail(new MemberEmail("kim@email.com"));

        // when & then
        assertThat(member).isNotEmpty();
    }
}

package shop.soolsool.member.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.soolsool.member.domain.model.Member;
import shop.soolsool.member.domain.model.vo.MemberEmail;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(final MemberEmail email);
}

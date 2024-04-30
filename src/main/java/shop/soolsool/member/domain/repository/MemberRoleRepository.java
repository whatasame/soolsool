package shop.soolsool.member.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.soolsool.member.domain.model.MemberRole;
import shop.soolsool.member.domain.model.vo.MemberRoleType;

@Repository
public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {

    Optional<MemberRole> findByName(final MemberRoleType name);
}
